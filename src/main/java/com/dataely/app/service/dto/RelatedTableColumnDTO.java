package com.dataely.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.RelatedTableColumn} entity.
 */
public class RelatedTableColumnDTO implements Serializable {

    private Long id;

    @NotNull
    private String columnName;

    private String columnType;

    private Instant creationDate;

    private Instant lastUpdated;

    private RelatedTableDTO relatedTable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
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

    public RelatedTableDTO getRelatedTable() {
        return relatedTable;
    }

    public void setRelatedTable(RelatedTableDTO relatedTable) {
        this.relatedTable = relatedTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelatedTableColumnDTO)) {
            return false;
        }

        RelatedTableColumnDTO relatedTableColumnDTO = (RelatedTableColumnDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, relatedTableColumnDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RelatedTableColumnDTO{" +
            "id=" + getId() +
            ", columnName='" + getColumnName() + "'" +
            ", columnType='" + getColumnType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", relatedTable=" + getRelatedTable() +
            "}";
    }
}
