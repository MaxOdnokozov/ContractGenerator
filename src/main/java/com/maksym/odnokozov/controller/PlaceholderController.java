package com.maksym.odnokozov.controller;

import com.maksym.odnokozov.service.PlaceholderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/placeholders")
@AllArgsConstructor
public class PlaceholderController {

    private final PlaceholderService service;
}
