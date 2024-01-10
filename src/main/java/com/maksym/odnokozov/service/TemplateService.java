package com.maksym.odnokozov.service;

import com.maksym.odnokozov.entity.Placeholder;
import com.maksym.odnokozov.entity.PlaceholderType;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
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
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    String filePath = Paths.get(TEMPLATES_DIR, fileName).toString();

    Path path = Paths.get(filePath);
    Files.createDirectories(path.getParent());
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

    List<Placeholder> placeholders = parsePlaceholders(path);

    User user = getUser(principal).orElseThrow(() -> new RuntimeException("cannot find user!"));

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
}
