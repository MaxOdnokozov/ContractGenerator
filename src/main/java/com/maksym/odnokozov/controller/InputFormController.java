package com.maksym.odnokozov.controller;

import com.maksym.odnokozov.service.InputFormService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/input_forms")
@AllArgsConstructor
public class InputFormController {

    private final InputFormService service;
}
