package com.dataely.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DsSchemaRelationship.
 */
@Entity
@Table(name = "ds_schema_relationship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DsSchemaRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "target")
    private String target;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dataSource", "dsSchemaRelationships", "tablesDefinitions" }, allowSetters = true)
    private DsSchema dsSchema;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DsSchemaRelationship id(Long id) {
        this.id = id;
        return this;
    }

    public String getSource() {
        return this.source;
    }

    public DsSchemaRelationship source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return this.target;
    }

    public DsSchemaRelationship target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public DsSchemaRelationship creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public DsSchemaRelationship lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public DsSchema getDsSchema() {
        return this.dsSchema;
    }

    public DsSchemaRelationship dsSchema(DsSchema dsSchema) {
        this.setDsSchema(dsSchema);
        return this;
    }

    public void setDsSchema(DsSchema dsSchema) {
        this.dsSchema = dsSchema;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DsSchemaRelationship)) {
            return false;
        }
        return id != null && id.equals(((DsSchemaRelationship) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DsSchemaRelationship{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", target='" + getTarget() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
