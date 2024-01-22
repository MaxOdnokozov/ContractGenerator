package com.maksym.odnokozov.repository;

import com.maksym.odnokozov.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

  Page<Template> findAllByOwnerId(Pageable pageable, Long ownerId);
}
