package com.example.demo.serviceImpl;

import com.example.demo.type.BackFieldHeaderType;
import com.example.demo.type.BarcodeType;
import com.example.demo.type.FrontFieldHeaderType;
import com.example.demo.type.QRType;
import com.example.demo.entity.Template;
import com.example.demo.handler.exception.NotExsistException;
import com.example.demo.prop.PassbookProperties;
import com.example.demo.repository.DemoRepository;
import com.example.demo.req.TemplateReq;
import com.example.demo.service.DemoService;
import com.example.demo.util.BarcodeUtil;
import com.example.demo.vo.TemplateVO;
import de.brendamour.jpasskit.PKBarcode;
import de.brendamour.jpasskit.PKField;
import de.brendamour.jpasskit.PKPass;
import de.brendamour.jpasskit.passes.PKStoreCard;
import de.brendamour.jpasskit.signing.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "demoService")
@RequiredArgsConstructor
public class DemoServiceImpl implements DemoService {

    @NonNull
    private PassbookProperties passbookProperties;

    @NonNull
    private DemoRepository demoRepository;

    @Override
    public List<TemplateVO> getPassbookTemplates() {
        List<Template> templateList = demoRepository.findAll();
        return templateList.stream().map(template -> TemplateVO.convertTemplateEntity(template)).collect(Collectors.toList());
    }

    @Override
    public TemplateVO getPassbookTemplate(String id) {
        Template template = demoRepository.findById(id).get();

        if (template == null) {
            throw new NotExsistException();
        }

        return TemplateVO.convertTemplateEntity(template);
    }

    @Override
    public byte[] generatePassbook(final TemplateReq templateReq) {

        TemplateVO templateVO = this.getPassbookTemplate(templateReq.getId());

        if (templateVO == null) {
            throw new NotExsistException();
        }

        ClassPathResource passbookResource = new ClassPathResource(passbookProperties.getCert().getCertPath());
        ClassPathResource wwrcResource = new ClassPathResource(passbookProperties.getCert().getWwrcPath());

        PKSigningInformation pkSigningInformation = null;
        try {
            pkSigningInformation = new PKSigningInformationUtil()
                    .loadSigningInformationFromPKCS12AndIntermediateCertificate(passbookResource.getInputStream(), passbookProperties.getCert().getPassword(), wwrcResource.getInputStream());

            PKFileBasedSigningUtil pkSigningUtil = new PKFileBasedSigningUtil();
            return pkSigningUtil.createSignedAndZippedPkPassArchive(this.generatePassInfo(templateVO, templateReq), this.generateTemplate(templateVO, templateReq.getBarcodeType(), templateReq.getMembershipNum()), pkSigningInformation);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (PKSigningException e) {
            e.printStackTrace();
        }

        return null;
    }

    private PKPass generatePassInfo(final TemplateVO templateVO, final TemplateReq templateReq) {
        PKPass pkPass = new PKPass();
        pkPass.setStoreCard(new PKStoreCard());
        pkPass.setSerialNumber(templateReq.getMembershipNum().replaceAll("\\s", ""));
        pkPass.setPassTypeIdentifier(passbookProperties.getStandard().getPassTypeIdentifier());
        pkPass.setTeamIdentifier(passbookProperties.getStandard().getTeamIdentifier());
        pkPass.setOrganizationName(templateVO.getName());
        pkPass.setDescription(templateVO.getDesc());

        if (templateVO.getStoreId() != null) {
            pkPass.setAssociatedStoreIdentifiers(Arrays.asList(templateVO.getStoreId().longValue()));
        }

        pkPass.setRelevantDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        this.addFrontField(pkPass, templateReq);
        this.addBackField(pkPass, templateReq);
        this.addQR(pkPass, templateReq.getQrType(), templateReq.getMembershipNum());

        return pkPass;
    }

    private PKPass addFrontField(final PKPass pkPass, final TemplateReq templateReq) {

        for (FrontFieldHeaderType frontFieldHeaderType : FrontFieldHeaderType.values()) {
            PKField pkField = null;

            switch (frontFieldHeaderType) {
                case NAME:
                    pkField = new PKField(FrontFieldHeaderType.NAME.toString(), FrontFieldHeaderType.NAME.getName(), templateReq.getName());
                    break;
                case MEMBERSHIP_NUM:
                default:
                    pkField = new PKField(FrontFieldHeaderType.MEMBERSHIP_NUM.toString(), FrontFieldHeaderType.MEMBERSHIP_NUM.getName(), templateReq.getDisplayMembershipNum());
                    break;
            }

            pkPass.getStoreCard().addSecondaryField(pkField);
        }

        return pkPass;
    }

    private PKPass addBackField(final PKPass pkPass, final TemplateReq templateReq) {

        for (BackFieldHeaderType backFieldHeaderType : BackFieldHeaderType.values()) {
            PKField pkField = null;

            switch (backFieldHeaderType) {
                case MEMBERSHIP_NUM:
                default:
                    pkField = new PKField(BackFieldHeaderType.MEMBERSHIP_NUM.toString(), BackFieldHeaderType.MEMBERSHIP_NUM.getName(), templateReq.getMembershipNum());
                    pkField.setDataDetectorTypes(Arrays.asList(BackFieldHeaderType.MEMBERSHIP_NUM.getDetectorType()));
                    break;
            }

            pkPass.getStoreCard().addBackField(pkField);
        }

        return pkPass;
    }

    private PKPass addQR(final PKPass pkPass, final QRType qrType, final String barcode) {
        List<PKBarcode> barcodes = pkPass.getBarcodes();

        if (barcodes == null) {
            barcodes = new ArrayList<PKBarcode>();
        }

        if (!QRType.NONE.equals(qrType)) {
            PKBarcode pkBarcode = new PKBarcode();
            pkBarcode.setMessage(barcode);
            pkBarcode.setFormat(qrType.getBarcodeFormat());
            pkBarcode.setMessageEncoding(Charset.forName("UTF-8"));
            barcodes.add(pkBarcode);
        }

        pkPass.setBarcodes(barcodes);

        return pkPass;
    }

    private PKPassTemplateInMemory generateTemplate(final TemplateVO templateVO, final BarcodeType barcodeType, final String barcode) {
        PKPassTemplateInMemory pkPassTemplateInMemory = new PKPassTemplateInMemory();

        try {
            ClassPathResource logoResource = new ClassPathResource(templateVO.getLogoPath());
            ClassPathResource logoRetinaResource = new ClassPathResource(templateVO.getLogoRetinaPath());

            //icon.png
            pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_ICON, logoResource.getInputStream());
            pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_ICON_RETINA, logoRetinaResource.getInputStream());

            //logo.png
            pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_LOGO, logoResource.getInputStream());
            pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_LOGO_RETINA, logoRetinaResource.getInputStream());

            //strip.png
            pkPassTemplateInMemory.addFile(
                    PKPassTemplateInMemory.PK_STRIP,
                    BarcodeUtil.generateBarcodeStream(barcodeType.getBarcodeFormat(), barcode, passbookProperties.getStrip().getWidth(), passbookProperties.getStrip().getHeight()));
            pkPassTemplateInMemory.addFile(
                    PKPassTemplateInMemory.PK_STRIP_RETINA,
                    BarcodeUtil.generateBarcodeStream(barcodeType.getBarcodeFormat(), barcode, passbookProperties.getStrip().getRetinaWidth(), passbookProperties.getStrip().getRetinaHeight()));
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        return pkPassTemplateInMemory;
    }
}
