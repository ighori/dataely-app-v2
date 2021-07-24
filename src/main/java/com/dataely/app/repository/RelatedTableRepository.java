package com.dataely.app.repository;

import com.dataely.app.domain.RelatedTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RelatedTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelatedTableRepository extends JpaRepository<RelatedTable, Long> {}
