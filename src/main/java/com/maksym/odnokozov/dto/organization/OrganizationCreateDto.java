package com.maksym.odnokozov.dto.organization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationCreateDto {

    private String name;
    private String description;
}


