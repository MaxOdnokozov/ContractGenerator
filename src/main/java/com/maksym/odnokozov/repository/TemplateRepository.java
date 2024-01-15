package com.maksym.odnokozov.repository;

import com.maksym.odnokozov.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    List<Template> findAllByOwnerId(Long ownerId);
}
