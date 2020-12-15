package com.example.demo.type;

import de.brendamour.jpasskit.enums.PKBarcodeFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum QRType {
    NONE("NONE", null),
    QR("QR", PKBarcodeFormat.PKBarcodeFormatQR),
    CODE128("CODE128", PKBarcodeFormat.PKBarcodeFormatCode128),
    PDF417("PDF417", PKBarcodeFormat.PKBarcodeFormatPDF417),
    AZTEC("AZTEC", PKBarcodeFormat.PKBarcodeFormatAztec);

    private String code;
    private PKBarcodeFormat barcodeFormat;

    public Optional<QRType> getQRType(String code) {
        return Arrays.stream(QRType.values()).filter(value -> value.getCode().equalsIgnoreCase(code)).findFirst();
    }
}