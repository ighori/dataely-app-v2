package com.dataely.app.repository;

import com.dataely.app.domain.TableRelationship;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TableRelationship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TableRelationshipRepository extends JpaRepository<TableRelationship, Long> {}
