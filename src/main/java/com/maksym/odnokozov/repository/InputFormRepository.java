package com.maksym.odnokozov.repository;

import com.maksym.odnokozov.entity.InputForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputFormRepository extends JpaRepository<InputForm, Long> {
}