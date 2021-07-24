package com.dataely.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TableColumn.
 */
@Entity
@Table(name = "table_column")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TableColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "column_name", nullable = false)
    private String columnName;

    @Column(name = "column_type")
    private String columnType;

    @Column(name = "column_size")
    private Long columnSize;

    @Column(name = "is_nullable")
    private Boolean isNullable;

    @Column(name = "is_primary_key")
    private Boolean isPrimaryKey;

    @Column(name = "is_foreign_key")
    private Boolean isForeignKey;

    @Column(name = "is_indexed")
    private Boolean isIndexed;

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

    public TableColumn id(Long id) {
        this.id = id;
        return this;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public TableColumn columnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public TableColumn columnType(String columnType) {
        this.columnType = columnType;
        return this;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Long getColumnSize() {
        return this.columnSize;
    }

    public TableColumn columnSize(Long columnSize) {
        this.columnSize = columnSize;
        return this;
    }

    public void setColumnSize(Long columnSize) {
        this.columnSize = columnSize;
    }

    public Boolean getIsNullable() {
        return this.isNullable;
    }

    public TableColumn isNullable(Boolean isNullable) {
        this.isNullable = isNullable;
        return this;
    }

    public void setIsNullable(Boolean isNullable) {
        this.isNullable = isNullable;
    }

    public Boolean getIsPrimaryKey() {
        return this.isPrimaryKey;
    }

    public TableColumn isPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
        return this;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public Boolean getIsForeignKey() {
        return this.isForeignKey;
    }

    public TableColumn isForeignKey(Boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
        return this;
    }

    public void setIsForeignKey(Boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public Boolean getIsIndexed() {
        return this.isIndexed;
    }

    public TableColumn isIndexed(Boolean isIndexed) {
        this.isIndexed = isIndexed;
        return this;
    }

    public void setIsIndexed(Boolean isIndexed) {
        this.isIndexed = isIndexed;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public TableColumn creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public TableColumn lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public TablesDefinition getTablesDefinition() {
        return this.tablesDefinition;
    }

    public TableColumn tablesDefinition(TablesDefinition tablesDefinition) {
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
        if (!(o instanceof TableColumn)) {
            return false;
        }
        return id != null && id.equals(((TableColumn) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TableColumn{" +
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
            "}";
    }
}
