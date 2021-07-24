package com.dataely.app.repository;

import com.dataely.app.domain.DsSchema;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DsSchema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DsSchemaRepository extends JpaRepository<DsSchema, Long> {}
