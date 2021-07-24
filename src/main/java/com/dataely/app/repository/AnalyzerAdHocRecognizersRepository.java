package com.dataely.app.repository;

import com.dataely.app.domain.AnalyzerAdHocRecognizers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnalyzerAdHocRecognizers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalyzerAdHocRecognizersRepository extends JpaRepository<AnalyzerAdHocRecognizers, Long> {}
