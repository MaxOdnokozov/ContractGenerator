package com.maksym.odnokozov.controller;

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
    public String uploadTemplate(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        try {
            templateService.saveTemplate(file);
            attributes.addFlashAttribute("message", "Template uploaded successfully");
        } catch (IOException e) {
            attributes.addFlashAttribute("message", "Failed to upload template");
        }
        return "redirect:/welcome";
    }
}
