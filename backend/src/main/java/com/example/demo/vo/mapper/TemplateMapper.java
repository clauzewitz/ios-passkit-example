package com.example.demo.vo.mapper;

import com.example.demo.entity.Template;
import com.example.demo.vo.TemplateVO;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TemplateMapper {
    @Getter
    private static final ModelMapper mapper = new ModelMapper();

    @PostConstruct
    public void setupMapper() {
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        mapper.createTypeMap(Template.class, TemplateVO.class)
                .addMappings(mapper -> {
                    mapper.map(s -> s.getId(), TemplateVO::setId);
                    mapper.map(s -> s.getStoreId(), TemplateVO::setStoreId);
                    mapper.map(s -> s.getName(), TemplateVO::setName);
                    mapper.map(s -> s.getDesc(), TemplateVO::setDesc);
                    mapper.map(s -> s.getThumbnail(), TemplateVO::setThumbnail);
                    mapper.map(s -> s.getLogoPath(), TemplateVO::setLogoPath);
                    mapper.map(s -> s.getLogoRetinaPath(), TemplateVO::setLogoRetinaPath);
                    mapper.map(s -> s.getContent(), TemplateVO::setContent);
                    mapper.map(s -> s.getTags(), TemplateVO::setTags);
                    mapper.map(s -> s.getCreateDate(), TemplateVO::setCreateDate);
                });
    }
}