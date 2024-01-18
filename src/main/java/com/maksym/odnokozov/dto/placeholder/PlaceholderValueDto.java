package com.maksym.odnokozov.dto.placeholder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceholderValueDto {

    private Long id;
    private String value;
    private Integer sequenceNumber;
}
