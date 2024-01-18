package com.maksym.odnokozov.controller;

import com.maksym.odnokozov.dto.template.TemplateDto;
import com.maksym.odnokozov.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/template-manager")
public class TemplateManagerController {
    
    private TemplateService templateService;

  @GetMapping("/templates")
  public String getTemplates(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      Principal principal,
      Model model) {
    Pageable pageable = PageRequest.of(page, size);
    
    Page<TemplateDto> templates =
        templateService.getTemplatesByPrincipal(pageable, principal);
    model.addAttribute("templates", templates);
    return "templates";
  }
}
