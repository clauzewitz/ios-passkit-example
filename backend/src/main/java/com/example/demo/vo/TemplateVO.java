package com.example.demo.vo;

import com.example.demo.vo.mapper.TemplateMapper;
import com.example.demo.entity.Template;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateVO {
    private String id;
    private Long storeId;
    private String name;
    private String desc;
    private String thumbnail;
    private String logoPath;
    private String logoRetinaPath;
    private String content;
    private List<String> tags;
    private long createDate;

    public void setStoreId(String storeId) {
        this.storeId = Long.parseLong(storeId);
    }

    @JsonIgnore
    public static TemplateVO convertTemplateEntity(final Template template) {
        return TemplateMapper.getMapper().map(template, TemplateVO.class);
    }
}
