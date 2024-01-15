package com.maksym.odnokozov.repository;

import com.maksym.odnokozov.entity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {}
