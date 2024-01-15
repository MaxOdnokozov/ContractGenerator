package com.maksym.odnokozov.dto.template.manager;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateManagerViewDto {
    
    private Long id;
    private String email;
    private String organization;
    private int templatesCount;
}
