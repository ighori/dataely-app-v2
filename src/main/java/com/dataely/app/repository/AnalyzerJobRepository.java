package com.dataely.app.repository;

import com.dataely.app.domain.AnalyzerJob;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AnalyzerJob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalyzerJobRepository extends JpaRepository<AnalyzerJob, Long> {
    @Query("select analyzerJob from AnalyzerJob analyzerJob where analyzerJob.user.login = ?#{principal.username}")
    List<AnalyzerJob> findByUserIsCurrentUser();
}
