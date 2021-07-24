package com.dataely.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DsSchema.
 */
@Entity
@Table(name = "ds_schema")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DsSchema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "detail")
    private String detail;

    @Column(name = "table_count")
    private Integer tableCount;

    @Column(name = "table_rel_count")
    private Integer tableRelCount;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "environment", "dsSchemas", "analyzerJobs", "analyzerResults" }, allowSetters = true)
    private DataSource dataSource;

    @OneToMany(mappedBy = "dsSchema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dsSchema" }, allowSetters = true)
    private Set<DsSchemaRelationship> dsSchemaRelationships = new HashSet<>();

    @OneToMany(mappedBy = "dsSchema")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dsSchema", "tableColumns" }, allowSetters = true)
    private Set<TablesDefinition> tablesDefinitions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DsSchema id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public DsSchema name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public DsSchema detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getTableCount() {
        return this.tableCount;
    }

    public DsSchema tableCount(Integer tableCount) {
        this.tableCount = tableCount;
        return this;
    }

    public void setTableCount(Integer tableCount) {
        this.tableCount = tableCount;
    }

    public Integer getTableRelCount() {
        return this.tableRelCount;
    }

    public DsSchema tableRelCount(Integer tableRelCount) {
        this.tableRelCount = tableRelCount;
        return this;
    }

    public void setTableRelCount(Integer tableRelCount) {
        this.tableRelCount = tableRelCount;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public DsSchema creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public DsSchema lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public DsSchema dataSource(DataSource dataSource) {
        this.setDataSource(dataSource);
        return this;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Set<DsSchemaRelationship> getDsSchemaRelationships() {
        return this.dsSchemaRelationships;
    }

    public DsSchema dsSchemaRelationships(Set<DsSchemaRelationship> dsSchemaRelationships) {
        this.setDsSchemaRelationships(dsSchemaRelationships);
        return this;
    }

    public DsSchema addDsSchemaRelationship(DsSchemaRelationship dsSchemaRelationship) {
        this.dsSchemaRelationships.add(dsSchemaRelationship);
        dsSchemaRelationship.setDsSchema(this);
        return this;
    }

    public DsSchema removeDsSchemaRelationship(DsSchemaRelationship dsSchemaRelationship) {
        this.dsSchemaRelationships.remove(dsSchemaRelationship);
        dsSchemaRelationship.setDsSchema(null);
        return this;
    }

    public void setDsSchemaRelationships(Set<DsSchemaRelationship> dsSchemaRelationships) {
        if (this.dsSchemaRelationships != null) {
            this.dsSchemaRelationships.forEach(i -> i.setDsSchema(null));
        }
        if (dsSchemaRelationships != null) {
            dsSchemaRelationships.forEach(i -> i.setDsSchema(this));
        }
        this.dsSchemaRelationships = dsSchemaRelationships;
    }

    public Set<TablesDefinition> getTablesDefinitions() {
        return this.tablesDefinitions;
    }

    public DsSchema tablesDefinitions(Set<TablesDefinition> tablesDefinitions) {
        this.setTablesDefinitions(tablesDefinitions);
        return this;
    }

    public DsSchema addTablesDefinition(TablesDefinition tablesDefinition) {
        this.tablesDefinitions.add(tablesDefinition);
        tablesDefinition.setDsSchema(this);
        return this;
    }

    public DsSchema removeTablesDefinition(TablesDefinition tablesDefinition) {
        this.tablesDefinitions.remove(tablesDefinition);
        tablesDefinition.setDsSchema(null);
        return this;
    }

    public void setTablesDefinitions(Set<TablesDefinition> tablesDefinitions) {
        if (this.tablesDefinitions != null) {
            this.tablesDefinitions.forEach(i -> i.setDsSchema(null));
        }
        if (tablesDefinitions != null) {
            tablesDefinitions.forEach(i -> i.setDsSchema(this));
        }
        this.tablesDefinitions = tablesDefinitions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DsSchema)) {
            return false;
        }
        return id != null && id.equals(((DsSchema) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DsSchema{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", tableCount=" + getTableCount() +
            ", tableRelCount=" + getTableRelCount() +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
