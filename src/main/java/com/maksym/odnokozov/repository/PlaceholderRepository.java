package com.maksym.odnokozov.repository;

import com.maksym.odnokozov.entity.Placeholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceholderRepository extends JpaRepository<Placeholder, Long> {
}
