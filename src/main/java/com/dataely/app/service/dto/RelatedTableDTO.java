package com.dataely.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.RelatedTable} entity.
 */
public class RelatedTableDTO implements Serializable {

    private Long id;

    @NotNull
    private String tableName;

    private Instant creationDate;

    private Instant lastUpdated;

    private TablesDefinitionDTO tablesDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public TablesDefinitionDTO getTablesDefinition() {
        return tablesDefinition;
    }

    public void setTablesDefinition(TablesDefinitionDTO tablesDefinition) {
        this.tablesDefinition = tablesDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelatedTableDTO)) {
            return false;
        }

        RelatedTableDTO relatedTableDTO = (RelatedTableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, relatedTableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RelatedTableDTO{" +
            "id=" + getId() +
            ", tableName='" + getTableName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", tablesDefinition=" + getTablesDefinition() +
            "}";
    }
}
