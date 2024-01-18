package com.maksym.odnokozov.dto.placeholder;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceholderDto {

    private Long id;
    private String name;
    private String description;
    private String type;
    private List<PlaceholderValueDto> predefinedValues;
}
