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
 * A TablesDefinition.
 */
@Entity
@Table(name = "tables_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TablesDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "value")
    private Integer value;

    @Column(name = "symbol_size")
    private Integer symbolSize;

    @Column(name = "category")
    private Integer category;

    @Column(name = "col_cnt")
    private Integer colCnt;

    @Column(name = "col_cnt_nbr")
    private Integer colCntNbr;

    @Column(name = "col_cnt_tb")
    private Integer colCntTB;

    @Column(name = "col_cnt_str")
    private Integer colCntSTR;

    @Column(name = "col_cnt_bl")
    private Integer colCntBL;

    @Column(name = "col_cnt_pk")
    private Integer colCntPK;

    @Column(name = "col_cnt_fk")
    private Integer colCntFK;

    @Column(name = "col_cnt_ix")
    private Integer colCntIX;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dataSource", "dsSchemaRelationships", "tablesDefinitions" }, allowSetters = true)
    private DsSchema dsSchema;

    @OneToMany(mappedBy = "tablesDefinition")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tablesDefinition" }, allowSetters = true)
    private Set<TableColumn> tableColumns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TablesDefinition id(Long id) {
        this.id = id;
        return this;
    }

    public String getTableName() {
        return this.tableName;
    }

    public TablesDefinition tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getValue() {
        return this.value;
    }

    public TablesDefinition value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getSymbolSize() {
        return this.symbolSize;
    }

    public TablesDefinition symbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
        return this;
    }

    public void setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
    }

    public Integer getCategory() {
        return this.category;
    }

    public TablesDefinition category(Integer category) {
        this.category = category;
        return this;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getColCnt() {
        return this.colCnt;
    }

    public TablesDefinition colCnt(Integer colCnt) {
        this.colCnt = colCnt;
        return this;
    }

    public void setColCnt(Integer colCnt) {
        this.colCnt = colCnt;
    }

    public Integer getColCntNbr() {
        return this.colCntNbr;
    }

    public TablesDefinition colCntNbr(Integer colCntNbr) {
        this.colCntNbr = colCntNbr;
        return this;
    }

    public void setColCntNbr(Integer colCntNbr) {
        this.colCntNbr = colCntNbr;
    }

    public Integer getColCntTB() {
        return this.colCntTB;
    }

    public TablesDefinition colCntTB(Integer colCntTB) {
        this.colCntTB = colCntTB;
        return this;
    }

    public void setColCntTB(Integer colCntTB) {
        this.colCntTB = colCntTB;
    }

    public Integer getColCntSTR() {
        return this.colCntSTR;
    }

    public TablesDefinition colCntSTR(Integer colCntSTR) {
        this.colCntSTR = colCntSTR;
        return this;
    }

    public void setColCntSTR(Integer colCntSTR) {
        this.colCntSTR = colCntSTR;
    }

    public Integer getColCntBL() {
        return this.colCntBL;
    }

    public TablesDefinition colCntBL(Integer colCntBL) {
        this.colCntBL = colCntBL;
        return this;
    }

    public void setColCntBL(Integer colCntBL) {
        this.colCntBL = colCntBL;
    }

    public Integer getColCntPK() {
        return this.colCntPK;
    }

    public TablesDefinition colCntPK(Integer colCntPK) {
        this.colCntPK = colCntPK;
        return this;
    }

    public void setColCntPK(Integer colCntPK) {
        this.colCntPK = colCntPK;
    }

    public Integer getColCntFK() {
        return this.colCntFK;
    }

    public TablesDefinition colCntFK(Integer colCntFK) {
        this.colCntFK = colCntFK;
        return this;
    }

    public void setColCntFK(Integer colCntFK) {
        this.colCntFK = colCntFK;
    }

    public Integer getColCntIX() {
        return this.colCntIX;
    }

    public TablesDefinition colCntIX(Integer colCntIX) {
        this.colCntIX = colCntIX;
        return this;
    }

    public void setColCntIX(Integer colCntIX) {
        this.colCntIX = colCntIX;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public TablesDefinition creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public TablesDefinition lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public DsSchema getDsSchema() {
        return this.dsSchema;
    }

    public TablesDefinition dsSchema(DsSchema dsSchema) {
        this.setDsSchema(dsSchema);
        return this;
    }

    public void setDsSchema(DsSchema dsSchema) {
        this.dsSchema = dsSchema;
    }

    public Set<TableColumn> getTableColumns() {
        return this.tableColumns;
    }

    public TablesDefinition tableColumns(Set<TableColumn> tableColumns) {
        this.setTableColumns(tableColumns);
        return this;
    }

    public TablesDefinition addTableColumn(TableColumn tableColumn) {
        this.tableColumns.add(tableColumn);
        tableColumn.setTablesDefinition(this);
        return this;
    }

    public TablesDefinition removeTableColumn(TableColumn tableColumn) {
        this.tableColumns.remove(tableColumn);
        tableColumn.setTablesDefinition(null);
        return this;
    }

    public void setTableColumns(Set<TableColumn> tableColumns) {
        if (this.tableColumns != null) {
            this.tableColumns.forEach(i -> i.setTablesDefinition(null));
        }
        if (tableColumns != null) {
            tableColumns.forEach(i -> i.setTablesDefinition(this));
        }
        this.tableColumns = tableColumns;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TablesDefinition)) {
            return false;
        }
        return id != null && id.equals(((TablesDefinition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TablesDefinition{" +
            "id=" + getId() +
            ", tableName='" + getTableName() + "'" +
            ", value=" + getValue() +
            ", symbolSize=" + getSymbolSize() +
            ", category=" + getCategory() +
            ", colCnt=" + getColCnt() +
            ", colCntNbr=" + getColCntNbr() +
            ", colCntTB=" + getColCntTB() +
            ", colCntSTR=" + getColCntSTR() +
            ", colCntBL=" + getColCntBL() +
            ", colCntPK=" + getColCntPK() +
            ", colCntFK=" + getColCntFK() +
            ", colCntIX=" + getColCntIX() +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
