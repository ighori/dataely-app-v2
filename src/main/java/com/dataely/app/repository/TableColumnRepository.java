package com.dataely.app.repository;

import com.dataely.app.domain.TableColumn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TableColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TableColumnRepository extends JpaRepository<TableColumn, Long> {}
