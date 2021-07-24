package com.dataely.app.repository;

import com.dataely.app.domain.Environment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Environment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {}
