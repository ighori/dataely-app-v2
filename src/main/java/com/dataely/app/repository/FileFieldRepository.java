package com.dataely.app.repository;

import com.dataely.app.domain.FileField;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FileField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileFieldRepository extends JpaRepository<FileField, Long> {}
