package com.maksym.odnokozov.controller;

import com.maksym.odnokozov.dto.template.TemplateViewDto;
import com.maksym.odnokozov.entity.Template;
import com.maksym.odnokozov.service.TemplateService;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/template")
@AllArgsConstructor
public class TemplateController {

  private final TemplateService templateService;

  @GetMapping("/upload")
  public String showUploadPage() {
    return "template-upload";
  }

  @PostMapping("/upload")
  public String uploadTemplate(
      @RequestParam("file") MultipartFile file,
      Principal principal,
      RedirectAttributes attributes) {
    try {
      Template savedTemplate = templateService.saveTemplate(file, principal);
      attributes.addFlashAttribute(
          "message", "Template uploaded successfully: " + savedTemplate.getName());
      return "redirect:/template/edit/" + savedTemplate.getId();
    } catch (IOException e) {
      e.printStackTrace();
      attributes.addFlashAttribute(
          "message", "Failed to upload template due to an error: " + e.getMessage());
    }
    return "redirect:/welcome";
  }

  @GetMapping("/edit/{id}")
  public String editTemplateForm(@PathVariable Long id, Model model) {
    TemplateViewDto template = templateService.getTemplateById(id);
    model.addAttribute("template", template);
    return "template-edit";
  }

  @PostMapping("/save")
  public String saveTemplate(
      @RequestParam Long templateId,
      @RequestParam List<String> placeholderTypes,
      @RequestParam List<String> placeholderDescriptions,
      @RequestParam Map<String, String> predefinedValues,
      RedirectAttributes attributes) {
    try {
      templateService.updateTemplatePlaceholders(
          templateId, placeholderTypes, placeholderDescriptions, predefinedValues);
      attributes.addFlashAttribute("message", "Template updated successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      attributes.addFlashAttribute(
          "message", "Failed to update template due to an error: " + e.getMessage());
    }
    return "redirect:/template-managers/templates";
  }
}
