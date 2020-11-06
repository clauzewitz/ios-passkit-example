package com.example.demo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateReq {


    @NonNull
    private String barcode;
    private String description;
}
