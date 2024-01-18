package com.maksym.odnokozov.dto.template;


import com.maksym.odnokozov.dto.template.manager.TemplateManagerViewDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateDto {

    private Long id;
    private String name;
    private String language;
    private TemplateManagerViewDto owner;
    private boolean isActive;
}
