package com.dataely.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.TablesDefinition} entity.
 */
public class TablesDefinitionDTO implements Serializable {

    private Long id;

    @NotNull
    private String tableName;

    private Integer value;

    private Integer symbolSize;

    private Integer category;

    private Integer colCnt;

    private Integer colCntNbr;

    private Integer colCntTB;

    private Integer colCntSTR;

    private Integer colCntBL;

    private Integer colCntPK;

    private Integer colCntFK;

    private Integer colCntIX;

    private Instant creationDate;

    private Instant lastUpdated;

    private DsSchemaDTO dsSchema;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getColCnt() {
        return colCnt;
    }

    public void setColCnt(Integer colCnt) {
        this.colCnt = colCnt;
    }

    public Integer getColCntNbr() {
        return colCntNbr;
    }

    public void setColCntNbr(Integer colCntNbr) {
        this.colCntNbr = colCntNbr;
    }

    public Integer getColCntTB() {
        return colCntTB;
    }

    public void setColCntTB(Integer colCntTB) {
        this.colCntTB = colCntTB;
    }

    public Integer getColCntSTR() {
        return colCntSTR;
    }

    public void setColCntSTR(Integer colCntSTR) {
        this.colCntSTR = colCntSTR;
    }

    public Integer getColCntBL() {
        return colCntBL;
    }

    public void setColCntBL(Integer colCntBL) {
        this.colCntBL = colCntBL;
    }

    public Integer getColCntPK() {
        return colCntPK;
    }

    public void setColCntPK(Integer colCntPK) {
        this.colCntPK = colCntPK;
    }

    public Integer getColCntFK() {
        return colCntFK;
    }

    public void setColCntFK(Integer colCntFK) {
        this.colCntFK = colCntFK;
    }

    public Integer getColCntIX() {
        return colCntIX;
    }

    public void setColCntIX(Integer colCntIX) {
        this.colCntIX = colCntIX;
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
        if (!(o instanceof TablesDefinitionDTO)) {
            return false;
        }

        TablesDefinitionDTO tablesDefinitionDTO = (TablesDefinitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tablesDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TablesDefinitionDTO{" +
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
            ", dsSchema=" + getDsSchema() +
            "}";
    }
}
