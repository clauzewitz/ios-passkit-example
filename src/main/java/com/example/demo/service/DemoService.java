package com.example.demo.service;

import com.example.demo.req.TemplateReq;

public interface DemoService {
    public byte[] generatePassbook(TemplateReq templateReq) throws Exception;
}
