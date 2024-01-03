package com.maksym.odnokozov.controller;

import com.maksym.odnokozov.service.PlaceholderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/placeholders")
@AllArgsConstructor
public class PlaceholderController {

    private final PlaceholderService service;
}
