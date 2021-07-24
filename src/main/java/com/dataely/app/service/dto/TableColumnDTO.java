package com.dataely.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.TableColumn} entity.
 */
public class TableColumnDTO implements Serializable {

    private Long id;

    @NotNull
    private String columnName;

    private String columnType;

    private Long columnSize;

    private Boolean isNullable;

    private Boolean isPrimaryKey;

    private Boolean isForeignKey;

    private Boolean isIndexed;

    private Instant creationDate;

    private Instant lastUpdated;

    private TablesDefinitionDTO tablesDefinition;

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

    public Long getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Long columnSize) {
        this.columnSize = columnSize;
    }

    public Boolean getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(Boolean isNullable) {
        this.isNullable = isNullable;
    }

    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public Boolean getIsForeignKey() {
        return isForeignKey;
    }

    public void setIsForeignKey(Boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public Boolean getIsIndexed() {
        return isIndexed;
    }

    public void setIsIndexed(Boolean isIndexed) {
        this.isIndexed = isIndexed;
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
        if (!(o instanceof TableColumnDTO)) {
            return false;
        }

        TableColumnDTO tableColumnDTO = (TableColumnDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tableColumnDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TableColumnDTO{" +
            "id=" + getId() +
            ", columnName='" + getColumnName() + "'" +
            ", columnType='" + getColumnType() + "'" +
            ", columnSize=" + getColumnSize() +
            ", isNullable='" + getIsNullable() + "'" +
            ", isPrimaryKey='" + getIsPrimaryKey() + "'" +
            ", isForeignKey='" + getIsForeignKey() + "'" +
            ", isIndexed='" + getIsIndexed() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", tablesDefinition=" + getTablesDefinition() +
            "}";
    }
}
