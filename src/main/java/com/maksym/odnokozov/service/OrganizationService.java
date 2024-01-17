package com.maksym.odnokozov.service;

import com.maksym.odnokozov.dto.organization.OrganizationDto;
import com.maksym.odnokozov.entity.Organization;
import com.maksym.odnokozov.repository.OrganizationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class OrganizationService {

  private final OrganizationRepository organizationRepository;

  public List<OrganizationDto> getOrganizations() {
    var organizations = (List<Organization>) organizationRepository.findAll();
    return organizations.stream()
        .map(
            organization ->
                OrganizationDto.builder()
                    .id(organization.getId())
                    .name(organization.getName())
                    .description(organization.getDescription())
                    .build())
        .collect(Collectors.toList());
  }
}
