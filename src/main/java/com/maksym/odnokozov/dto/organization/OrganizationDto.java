package com.maksym.odnokozov.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDto {

    private Long id;
    private String name;
    private String description;
}
