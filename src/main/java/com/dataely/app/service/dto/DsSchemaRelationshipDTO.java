package com.dataely.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dataely.app.domain.DsSchemaRelationship} entity.
 */
public class DsSchemaRelationshipDTO implements Serializable {

    private Long id;

    private String source;

    private String target;

    private Instant creationDate;

    private Instant lastUpdated;

    private DsSchemaDTO dsSchema;

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

    public DsSchemaDTO getDsSchema() {
        return dsSchema;
    }

    public void setDsSchema(DsSchemaDTO dsSchema) {
        this.dsSchema = dsSchema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DsSchemaRelationshipDTO)) {
            return false;
        }

        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = (DsSchemaRelationshipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dsSchemaRelationshipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DsSchemaRelationshipDTO{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", target='" + getTarget() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", dsSchema=" + getDsSchema() +
            "}";
    }
}
