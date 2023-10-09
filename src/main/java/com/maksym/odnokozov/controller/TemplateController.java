package com.maksym.odnokozov.controller;

import com.maksym.odnokozov.service.TemplateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/templates")
@AllArgsConstructor
public class TemplateController {

    private final TemplateService service;
}
