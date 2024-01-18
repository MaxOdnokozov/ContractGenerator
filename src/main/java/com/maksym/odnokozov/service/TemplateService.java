package com.maksym.odnokozov.service;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;
import static java.util.Objects.nonNull;

import com.maksym.odnokozov.dto.placeholder.PlaceholderDto;
import com.maksym.odnokozov.dto.placeholder.PlaceholderValueDto;
import com.maksym.odnokozov.dto.template.TemplateDto;
import com.maksym.odnokozov.dto.template.TemplateViewDto;
import com.maksym.odnokozov.dto.template.manager.TemplateManagerViewDto;
import com.maksym.odnokozov.entity.Placeholder;
import com.maksym.odnokozov.entity.PlaceholderType;
import com.maksym.odnokozov.entity.PlaceholderValue;
import com.maksym.odnokozov.entity.Template;
import com.maksym.odnokozov.entity.User;
import com.maksym.odnokozov.repository.TemplateRepository;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@AllArgsConstructor
public class TemplateService {

  private static final String TEMPLATES_DIR = "templates";
  private static final String PLACEHOLDER_PATTERN = "\\$\\{(.+?)}";

  private final TemplateRepository templateRepository;
  private final PlaceholderService placeholderService;

  @Transactional
  public Template saveTemplate(MultipartFile file, Principal principal) throws IOException {
    User user = getUser(principal).orElseThrow(() -> new RuntimeException("cannot find user!"));

    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    String filePath = Paths.get(TEMPLATES_DIR, user.getEmail(), fileName).toString();

    Path path = Paths.get(filePath);
    Files.createDirectories(path.getParent());
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

    List<Placeholder> placeholders = parsePlaceholders(path);

    Template template =
        Template.builder()
            .name(fileName)
            .filePath(filePath)
            .owner(user)
            .placeholders(placeholders)
            .build();

    placeholders.forEach(placeholder -> placeholder.setTemplate(template));

    var savedPlaceholders = placeholderService.savePlaceholders(placeholders);
    log.info("Placeholders saved: {}", savedPlaceholders);
    return templateRepository.save(template);
  }

  private Optional<User> getUser(Principal principal) {
    if (principal instanceof UsernamePasswordAuthenticationToken token) {
      Object principalUser = token.getPrincipal();

      if (principalUser instanceof User) {
        return Optional.of((User) principalUser);
      }
    }

    return Optional.empty();
  }

  private List<Placeholder> parsePlaceholders(Path filePath) throws IOException {
    List<Placeholder> placeholders = new ArrayList<>();
    try (FileInputStream fis = new FileInputStream(filePath.toFile());
        XWPFDocument document = new XWPFDocument(fis)) {
      for (XWPFParagraph p : document.getParagraphs()) {
        String paragraphText = p.getText();
        Matcher matcher = Pattern.compile(PLACEHOLDER_PATTERN).matcher(paragraphText);
        while (matcher.find()) {
          String placeholderName = matcher.group(1);
          if (placeholders.stream().noneMatch(ph -> ph.getName().equals(placeholderName))) {
            Placeholder placeholder =
                Placeholder.builder().name(placeholderName).type(PlaceholderType.TEXT).build();
            placeholders.add(placeholder);
          }
        }
      }
    }
    return placeholders;
  }

  public Page<TemplateDto> getTemplatesByPrincipal(Pageable pageable, Principal principal) {
    User user = getUser(principal).orElseThrow(() -> new RuntimeException("cannot find user!"));

    return templateRepository
        .findAllByOwnerId(pageable, user.getId())
        .map(
            template ->
                TemplateDto.builder()
                    .id(template.getId())
                    .name(template.getName())
                    .language(template.getLanguage().name())
                    .isActive(template.isActive())
                    .owner(
                        TemplateManagerViewDto.builder()
                            .id(template.getOwner().getId())
                            .email(template.getOwner().getEmail())
                            .organization(
                                nonNull(template.getOwner().getOrganization())
                                    ? template.getOwner().getOrganization().getName()
                                    : EMPTY_STRING)
                            .build())
                    .build());
  }

  public TemplateViewDto getTemplateById(Long id) {
    return templateRepository
        .findById(id)
        .map(this::mapToTemplateViewDto)
        .orElseThrow(() -> new RuntimeException("Cannot find template by id=" + id));
  }

  private TemplateViewDto mapToTemplateViewDto(Template template) {
    return TemplateViewDto.builder()
        .id(template.getId())
        .name(template.getName())
        .organizationName(template.getOrganization().getName())
        .language(template.getLanguage().name())
        .isActive(template.isActive())
        .placeholders(
            template.getPlaceholders().stream()
                .map(this::mapToPlaceholderDto)
                .collect(Collectors.toList()))
        .build();
  }

  private PlaceholderDto mapToPlaceholderDto(Placeholder placeholder) {
    return PlaceholderDto.builder()
        .id(placeholder.getId())
        .name(placeholder.getName())
        .description(placeholder.getDescription())
        .type(placeholder.getType().name())
        .predefinedValues(
            placeholder.getPredefinedValues().stream()
                .map(this::mapToPlaceholderValueDto)
                .collect(Collectors.toList()))
        .build();
  }

  private PlaceholderValueDto mapToPlaceholderValueDto(PlaceholderValue value) {
    return PlaceholderValueDto.builder()
        .value(value.getValue())
        .sequenceNumber(value.getSequenceNumber())
        .id(value.getId())
        .build();
  }
}
