package com.maksym.odnokozov.repository;

import com.maksym.odnokozov.entity.Role;
import com.maksym.odnokozov.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Page<User> findByRole(@Param("role") Role role, Pageable pageable);
}
