package com.example.demo.type;

import com.google.zxing.BarcodeFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum BarcodeType {
    NONE("NONE", null),
    CODABAR("CODABAR", BarcodeFormat.CODABAR),
    CODE128("CODE128", BarcodeFormat.CODE_128),
    PDF417("PDF417", BarcodeFormat.PDF_417);

    private String code;
    private BarcodeFormat barcodeFormat;

    public Optional<BarcodeType> getBarcodeType(String code) {
        return Arrays.stream(BarcodeType.values()).filter(value -> value.getCode().equalsIgnoreCase(code)).findFirst();
    }
}
