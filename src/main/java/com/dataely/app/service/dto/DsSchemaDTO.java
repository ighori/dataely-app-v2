package com.dataely.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.DsSchema} entity.
 */
public class DsSchemaDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String detail;

    private Integer tableCount;

    private Integer tableRelCount;

    private Instant creationDate;

    private Instant lastUpdated;

    private DataSourceDTO dataSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getTableCount() {
        return tableCount;
    }

    public void setTableCount(Integer tableCount) {
        this.tableCount = tableCount;
    }

    public Integer getTableRelCount() {
        return tableRelCount;
    }

    public void setTableRelCount(Integer tableRelCount) {
        this.tableRelCount = tableRelCount;
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

    public DataSourceDTO getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSourceDTO dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DsSchemaDTO)) {
            return false;
        }

        DsSchemaDTO dsSchemaDTO = (DsSchemaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dsSchemaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DsSchemaDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", tableCount=" + getTableCount() +
            ", tableRelCount=" + getTableRelCount() +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", dataSource=" + getDataSource() +
            "}";
    }
}
