package com.dataely.app.repository;

import com.dataely.app.domain.AnalyzerResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnalyzerResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalyzerResultRepository extends JpaRepository<AnalyzerResult, Long> {}
