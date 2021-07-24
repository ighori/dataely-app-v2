package com.dataely.app.repository;

import com.dataely.app.domain.FileConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FileConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileConfigRepository extends JpaRepository<FileConfig, Long> {}
