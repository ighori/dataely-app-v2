package com.dataely.app.repository;

import com.dataely.app.domain.TablesDefinition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TablesDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TablesDefinitionRepository extends JpaRepository<TablesDefinition, Long> {}
