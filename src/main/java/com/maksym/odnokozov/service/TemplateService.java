package com.maksym.odnokozov.service;

import com.maksym.odnokozov.repository.TemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TemplateService {

    private final TemplateRepository repository;
}
