package com.maksym.odnokozov.service;

import com.maksym.odnokozov.repository.PlaceholderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceholderService {

    private final PlaceholderRepository repository;
}
