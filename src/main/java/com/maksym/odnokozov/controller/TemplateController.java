package com.maksym.odnokozov.controller;

import com.maksym.odnokozov.entity.Template;
import com.maksym.odnokozov.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

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
    public String uploadTemplate(@RequestParam("file") MultipartFile file,
                                 Principal principal,
                                 RedirectAttributes attributes) {
        try {
            Template savedTemplate = templateService.saveTemplate(file, principal);
            attributes.addFlashAttribute("message", "Template uploaded successfully: " + savedTemplate.getName());
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception, for real applications, use a logger
            attributes.addFlashAttribute("message", "Failed to upload template due to an error: " + e.getMessage());
        }
        return "redirect:/welcome";
    }
}
