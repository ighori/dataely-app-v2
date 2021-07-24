package com.dataely.app.repository;

import com.dataely.app.domain.AnalyzerEntities;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnalyzerEntities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalyzerEntitiesRepository extends JpaRepository<AnalyzerEntities, Long> {}
