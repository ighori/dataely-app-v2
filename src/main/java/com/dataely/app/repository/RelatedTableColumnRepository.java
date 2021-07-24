package com.dataely.app.repository;

import com.dataely.app.domain.RelatedTableColumn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RelatedTableColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatedTableColumnRepository extends JpaRepository<RelatedTableColumn, Long> {}
