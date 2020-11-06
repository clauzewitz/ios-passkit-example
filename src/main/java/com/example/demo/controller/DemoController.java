package com.example.demo.controller;

import com.example.demo.req.TemplateReq;
import com.example.demo.service.DemoService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;


    @PostMapping(path = "/passbook")
    public ResponseEntity<Resource> generatePassbook(@RequestBody TemplateReq templateReq, HttpServletResponse response) throws Exception {
        byte[] passbook = demoService.generatePassbook(templateReq);


        if (passbook == null || ArrayUtils.isEmpty(passbook)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            return ResponseEntity.ok()
                    .contentLength(passbook.length)
                    .contentType(MediaType.valueOf("application/vnd.apple.pkpass"))
                    .body(new InputStreamResource(new ByteArrayInputStream(passbook)));
        }
    }
}
