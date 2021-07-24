package com.dataely.app.repository;

import com.dataely.app.domain.DsSchemaRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DsSchemaRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DsSchemaRelationshipRepository extends JpaRepository<DsSchemaRelationship, Long> {}
