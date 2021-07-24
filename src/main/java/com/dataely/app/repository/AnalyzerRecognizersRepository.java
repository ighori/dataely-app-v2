package com.dataely.app.repository;

import com.dataely.app.domain.AnalyzerRecognizers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnalyzerRecognizers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalyzerRecognizersRepository extends JpaRepository<AnalyzerRecognizers, Long> {}
