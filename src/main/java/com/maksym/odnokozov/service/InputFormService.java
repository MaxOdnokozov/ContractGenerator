package com.maksym.odnokozov.service;

import com.maksym.odnokozov.repository.InputFormRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InputFormService {

    private final InputFormRepository repository;
}
