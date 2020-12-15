package com.example.demo.controller;

import com.example.demo.req.TemplateReq;
import com.example.demo.service.DemoService;
import com.example.demo.vo.TemplateVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DemoController {

    @NonNull
    private DemoService demoService;

    @GetMapping(path = "/templates")
    public ResponseEntity<List<TemplateVO>> getPassbookTemplates() {
        return ResponseEntity.ok().body(demoService.getPassbookTemplates());
    }

    @GetMapping(path = "/templates/{id}")
    public ResponseEntity<TemplateVO> getPassbookTemplate(@PathVariable String id) {
        return ResponseEntity.ok().body(demoService.getPassbookTemplate(id));
    }

    @PostMapping(path = "/passbook")
    public ResponseEntity<Resource> generatePassbook(@RequestBody TemplateReq templateReq, HttpServletResponse response) {
        byte[] passbook = demoService.generatePassbook(templateReq);

//        response.setStatus(HttpStatus.OK.value());
//        response.setContentLength(passbook.length);
//        response.setContentType("application/vnd.apple.pkpass");
//        response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", "asdfaf"));
//
//        try {
//            IOUtils.copy(new ByteArrayInputStream(passbook), response.getOutputStream());
//            response.flushBuffer();
//
//
//        } catch (IOException e) {
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        }

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
