package com.dataely.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TableRelationship.
 */
@Entity
@Table(name = "table_relationship")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TableRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "target")
    private String target;

    @Column(name = "source_key")
    private String sourceKey;

    @Column(name = "target_key")
    private String targetKey;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dsSchema", "tableColumns" }, allowSetters = true)
    private TablesDefinition tablesDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TableRelationship id(Long id) {
        this.id = id;
        return this;
    }

    public String getSource() {
        return this.source;
    }

    public TableRelationship source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return this.target;
    }

    public TableRelationship target(String target) {
        this.target = target;
        return this;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSourceKey() {
        return this.sourceKey;
    }

    public TableRelationship sourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
        return this;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getTargetKey() {
        return this.targetKey;
    }

    public TableRelationship targetKey(String targetKey) {
        this.targetKey = targetKey;
        return this;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public TableRelationship creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public TableRelationship lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public TablesDefinition getTablesDefinition() {
        return this.tablesDefinition;
    }

    public TableRelationship tablesDefinition(TablesDefinition tablesDefinition) {
        this.setTablesDefinition(tablesDefinition);
        return this;
    }

    public void setTablesDefinition(TablesDefinition tablesDefinition) {
        this.tablesDefinition = tablesDefinition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TableRelationship)) {
            return false;
        }
        return id != null && id.equals(((TableRelationship) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TableRelationship{" +
            "id=" + getId() +
            ", source='" + getSource() + "'" +
            ", target='" + getTarget() + "'" +
            ", sourceKey='" + getSourceKey() + "'" +
            ", targetKey='" + getTargetKey() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
