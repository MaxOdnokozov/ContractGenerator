package com.maksym.odnokozov.service;

import com.maksym.odnokozov.entity.Placeholder;
import com.maksym.odnokozov.repository.PlaceholderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaceholderService {

    private final PlaceholderRepository repository;

    public List<Placeholder> savePlaceholders(List<Placeholder> placeholders) {
        return repository.saveAll(placeholders);
    }
}
