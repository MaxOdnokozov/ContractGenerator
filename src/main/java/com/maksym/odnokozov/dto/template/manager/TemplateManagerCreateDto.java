package com.maksym.odnokozov.dto.template.manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateManagerCreateDto {

  private Long organizationId;
  private String email;
  private String password;
}
