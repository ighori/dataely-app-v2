package com.dataely.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dataely.app.domain.TableRelationship} entity.
 */
public class TableRelationshipDTO implements Serializable {

    private Long id;

    private String source;

    private String target;

    private String sourceKey;

    private String targetKey;

    private Instant creationDate;

    private Instant lastUpdated;

    private TablesDefinitionDTO tablesDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
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
        if (!(o instanceof TableRelationshipDTO)) {
            return false;
        }

        TableRelationshipDTO tableRelationshipDTO = (TableRelationshipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tableRelationshipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TableRelationshipDTO{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", target='" + getTarget() + "'" +
            ", sourceKey='" + getSourceKey() + "'" +
            ", targetKey='" + getTargetKey() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", tablesDefinition=" + getTablesDefinition() +
            "}";
    }
}
