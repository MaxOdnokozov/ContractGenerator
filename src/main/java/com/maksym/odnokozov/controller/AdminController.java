package com.maksym.odnokozov.controller;

import com.maksym.odnokozov.dto.template.manager.TemplateManagerViewDto;
import com.maksym.odnokozov.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;

    @GetMapping("/admin/template-managers")
    public String getTemplateManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        log.info("Getting users for table");
        Page<TemplateManagerViewDto> templateManagersPage = adminService.getTemplateManagers(PageRequest.of(page, size));
        model.addAttribute("templateManagersPage", templateManagersPage);
        return "template-managers";
    }
}
