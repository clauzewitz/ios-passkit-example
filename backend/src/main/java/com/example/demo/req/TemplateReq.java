package com.example.demo.req;

import com.example.demo.type.BarcodeType;
import com.example.demo.type.QRType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateReq {
    @NonNull
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String membershipNum;
    @NonNull
    private String displayMembershipNum;
    @NonNull
    private BarcodeType barcodeType;
    @NonNull
    private QRType qrType;
}
