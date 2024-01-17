package com.maksym.odnokozov.controller;

import com.maksym.odnokozov.dto.organization.OrganizationCreateDto;
import com.maksym.odnokozov.dto.organization.OrganizationDto;
import com.maksym.odnokozov.dto.template.manager.TemplateManagerCreateDto;
import com.maksym.odnokozov.dto.template.manager.TemplateManagerViewDto;
import com.maksym.odnokozov.service.AdminService;
import com.maksym.odnokozov.service.OrganizationService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private AdminService adminService;
  private OrganizationService organizationService;

  @GetMapping("/template-managers")
  public String getTemplateManagers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      Model model) {
    Page<TemplateManagerViewDto> templateManagersPage =
        adminService.getTemplateManagers(PageRequest.of(page, size));
    model.addAttribute("templateManagersPage", templateManagersPage);
    return "template-managers";
  }

  @GetMapping("/template-managers/create")
  public String addTemplateManagerForm(Model model) {
    List<OrganizationDto> organizations = organizationService.getOrganizations();
    model.addAttribute("templateManagerCreateDto", new TemplateManagerCreateDto());
    model.addAttribute("organizations", organizations);
    return "template-managers-create";
  }

  @PostMapping("/template-managers")
  public String createTemplateManager(
      @ModelAttribute TemplateManagerCreateDto templateManagerCreateDto) {
    adminService.createTemplateManager(templateManagerCreateDto);
    return "redirect:/admin/template-managers";
  }

  @GetMapping("/organizations")
  public String getOrganizations(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      Model model) {
    Page<OrganizationDto> organizationsPage =
        adminService.getOrganizations(PageRequest.of(page, size));
    model.addAttribute("organizationsPage", organizationsPage);
    return "organizations";
  }

  @GetMapping("/organizations/create")
  public String createOrganizationForm(Model model) {
    model.addAttribute("organizationCreateDto", new OrganizationCreateDto());
    return "organizations-create";
  }

  @PostMapping("/organizations")
  public String createOrganization(@ModelAttribute OrganizationCreateDto organizationCreateDto) {
    adminService.createOrganization(organizationCreateDto);
    return "redirect:/admin/organizations";
  }
}
