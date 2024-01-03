package com.maksym.odnokozov.service;

import com.maksym.odnokozov.entity.Placeholder;
import com.maksym.odnokozov.entity.Template;
import com.maksym.odnokozov.repository.TemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public Template saveTemplate(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = "templates/" + fileName;

        // Save file to disk
        Path path = Paths.get(filePath);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Parse placeholders
        List<Placeholder> placeholders = parsePlaceholders(path);

        // Save template info to database
        Template template = new Template();
        template.setName(fileName);
        template.setFilePath(filePath);
        template.setPlaceholders(placeholders);
        return templateRepository.save(template);
    }

    private List<Placeholder> parsePlaceholders(Path filePath) throws IOException {
        String content = new String(Files.readAllBytes(filePath));
        Matcher matcher = Pattern.compile("\\$\\{(.+?)\\}").matcher(content);
        List<Placeholder> placeholders = new ArrayList<>();
        while (matcher.find()) {
            Placeholder placeholder = new Placeholder();
            placeholder.setName(matcher.group(1));
            placeholders.add(placeholder);
        }
        return placeholders;
    }
}
