package com.maksym.odnokozov.service;

import static java.util.Objects.nonNull;

import com.maksym.odnokozov.dto.organization.OrganizationCreateDto;
import com.maksym.odnokozov.dto.organization.OrganizationDto;
import com.maksym.odnokozov.dto.template.manager.TemplateManagerCreateDto;
import com.maksym.odnokozov.dto.template.manager.TemplateManagerViewDto;
import com.maksym.odnokozov.entity.Organization;
import com.maksym.odnokozov.entity.Role;
import com.maksym.odnokozov.entity.User;
import com.maksym.odnokozov.repository.OrganizationRepository;
import com.maksym.odnokozov.repository.TemplateRepository;
import com.maksym.odnokozov.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AdminService {

  private static final String EMPTY_STRING = "";

  private final UserRepository userRepository;
  private final TemplateRepository templateRepository;
  private final OrganizationRepository organizationRepository;
  private final PasswordEncoder passwordEncoder;

  public Page<TemplateManagerViewDto> getTemplateManagers(Pageable pageable) {
    Page<User> usersPage = userRepository.findByRole(Role.TEMPLATE_MANAGER, pageable);
    return usersPage.map(
        user ->
            TemplateManagerViewDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .organization(
                    nonNull(user.getOrganization())
                        ? user.getOrganization().getName()
                        : EMPTY_STRING)
                .templatesCount(nonNull(user.getTemplates()) ? user.getTemplates().size() : 0)
                .build());
  }

  public Page<OrganizationDto> getOrganizations(Pageable pageable) {
    Page<Organization> organizationPage = organizationRepository.findAll(pageable);
    return organizationPage.map(
        organization ->
            OrganizationDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .build());
  }

  public void createOrganization(OrganizationCreateDto organizationCreateDto) {
    organizationRepository.save(
        Organization.builder()
            .name(organizationCreateDto.getName())
            .description(
                organizationCreateDto.getDescription().isBlank()
                    ? null
                    : organizationCreateDto.getDescription())
            .build());
  }

  public void createTemplateManager(TemplateManagerCreateDto createDto) {
    userRepository.save(
        User.builder()
            .email(createDto.getEmail())
            .organization(Organization.builder().id(createDto.getOrganizationId()).build())
            .password(passwordEncoder.encode(createDto.getPassword()))
            .role(Role.TEMPLATE_MANAGER)
            .build());
  }
}
