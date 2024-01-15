package com.maksym.odnokozov.service;

import static java.util.Objects.nonNull;

import com.maksym.odnokozov.dto.template.manager.TemplateManagerViewDto;
import com.maksym.odnokozov.entity.Role;
import com.maksym.odnokozov.entity.User;
import com.maksym.odnokozov.repository.OrganizationRepository;
import com.maksym.odnokozov.repository.TemplateRepository;
import com.maksym.odnokozov.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AdminService {

  private final UserRepository userRepository;
  private final TemplateRepository templateRepository;
  private final OrganizationRepository organizationRepository;

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
                        : StringUtils.EMPTY)
                .templatesCount(nonNull(user.getTemplates()) ? user.getTemplates().size() : 0)
                .build());
  }
}
