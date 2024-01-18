package com.maksym.odnokozov.dto.template;

import com.maksym.odnokozov.dto.placeholder.PlaceholderDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateViewDto {
    
    private Long id;
    private String name;
    private boolean isActive;
    private String language;
    private List<PlaceholderDto> placeholders;
    private String organizationName;
}
