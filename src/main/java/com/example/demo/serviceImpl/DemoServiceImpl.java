package com.example.demo.serviceImpl;

import com.example.demo.prop.PassbookProperties;
import com.example.demo.repository.DemoRepository;
import com.example.demo.req.TemplateReq;
import com.example.demo.service.DemoService;
import com.example.demo.util.BarcodeUtil;
import de.brendamour.jpasskit.PKBarcode;
import de.brendamour.jpasskit.PKPass;
import de.brendamour.jpasskit.enums.PKBarcodeFormat;
import de.brendamour.jpasskit.passes.PKStoreCard;
import de.brendamour.jpasskit.signing.PKFileBasedSigningUtil;
import de.brendamour.jpasskit.signing.PKPassTemplateInMemory;
import de.brendamour.jpasskit.signing.PKSigningInformation;
import de.brendamour.jpasskit.signing.PKSigningInformationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "demoService")
public class DemoServiceImpl implements DemoService {

    @Autowired
    private PassbookProperties passbookProperties;


    @Autowired
    private DemoRepository demoRepository;

    @Override
    public byte[] generatePassbook(TemplateReq templateReq) throws Exception {
        ClassPathResource passbookResource = new ClassPathResource(passbookProperties.getCert().getCertPath());
        ClassPathResource wwrcResource = new ClassPathResource(passbookProperties.getCert().getWwrcPath());

        PKSigningInformation pkSigningInformation = new PKSigningInformationUtil()
                .loadSigningInformationFromPKCS12AndIntermediateCertificate(passbookResource.getInputStream(), passbookProperties.getCert().getPassword(), wwrcResource.getInputStream());
        PKFileBasedSigningUtil pkSigningUtil = new PKFileBasedSigningUtil();

        return pkSigningUtil.createSignedAndZippedPkPassArchive(this.generatePassInfo(templateReq.getBarcode()), this.generateTemplate(templateReq.getBarcode()), pkSigningInformation);
    }

    private PKPass generatePassInfo(String barcode) {
        PKPass pkPass = new PKPass();
        pkPass.setStoreCard(new PKStoreCard());
        pkPass.setSerialNumber(barcode.replaceAll("\\s", ""));
        pkPass.setPassTypeIdentifier(passbookProperties.getStandard().getPassTypeIdentifier());
        pkPass.setTeamIdentifier(passbookProperties.getStandard().getTeamIdentifier());
        pkPass.setOrganizationName("test");
        pkPass.setDescription("test");
        pkPass.setRelevantDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        List<PKBarcode> barcodeList = new ArrayList<PKBarcode>();
        PKBarcode pkBarcode = new PKBarcode();
        pkBarcode.setMessage(barcode);
        pkBarcode.setFormat(PKBarcodeFormat.PKBarcodeFormatQR);
        pkBarcode.setMessageEncoding(Charset.forName("UTF-8"));
        barcodeList.add(pkBarcode);

        pkPass.setBarcodes(barcodeList);

        return pkPass;
    }

    private PKPassTemplateInMemory generateTemplate(String barcode) {
        PKPassTemplateInMemory pkPassTemplateInMemory = new PKPassTemplateInMemory();

        try {
            //icon.png
            pkPassTemplateInMemory.addFile(
                    PKPassTemplateInMemory.PK_ICON,
                    BarcodeUtil.generateBarcodeStream(barcode, passbookProperties.getIcon().getName(), passbookProperties.getIcon().getWidth(), passbookProperties.getIcon().getHeight()));
            pkPassTemplateInMemory.addFile(
                    PKPassTemplateInMemory.PK_ICON_RETINA,
                    BarcodeUtil.generateBarcodeStream(barcode, passbookProperties.getIcon().getRetinaName(), passbookProperties.getIcon().getRetinaWidth(), passbookProperties.getIcon().getRetinaHeight()));

            //logo.png
//            pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_LOGO, new File(""));
//            pkPassTemplateInMemory.addFile(PKPassTemplateInMemory.PK_LOGO_RETINA, new File(""));

            //strip.png
            pkPassTemplateInMemory.addFile(
                    PKPassTemplateInMemory.PK_STRIP,
                    BarcodeUtil.generateBarcodeStream(barcode, passbookProperties.getStrip().getName(), passbookProperties.getStrip().getWidth(), passbookProperties.getStrip().getHeight()));
            pkPassTemplateInMemory.addFile(
                    PKPassTemplateInMemory.PK_STRIP_RETINA,
                    BarcodeUtil.generateBarcodeStream(barcode, passbookProperties.getStrip().getRetinaName(), passbookProperties.getStrip().getRetinaWidth(), passbookProperties.getStrip().getRetinaHeight()));
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        return pkPassTemplateInMemory;
    }
}
