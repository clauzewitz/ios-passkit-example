package com.example.demo.service;

import com.example.demo.req.TemplateReq;
import com.example.demo.vo.TemplateVO;

import java.util.List;

public interface DemoService {
    public List<TemplateVO> getPassbookTemplates();
    public TemplateVO getPassbookTemplate(String id);
    public byte[] generatePassbook(final TemplateReq templateReq);
}
