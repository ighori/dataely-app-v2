package com.dataely.app.repository;

import com.dataely.app.domain.FileSource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FileSource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileSourceRepository extends JpaRepository<FileSource, Long> {}
