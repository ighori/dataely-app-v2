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
 * A FileConfig.
 */
@Entity
@Table(name = "file_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FileConfig implements Serializable {

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

    @Column(name = "column_name_line_number")
    private Integer columnNameLineNumber;

    @Column(name = "encoding")
    private String encoding;

    @Column(name = "separator_char")
    private String separatorChar;

    @Column(name = "quote_char")
    private String quoteChar;

    @Column(name = "escape_char")
    private String escapeChar;

    @Column(name = "fixed_value_width")
    private Integer fixedValueWidth;

    @Column(name = "skip_empty_lines")
    private Boolean skipEmptyLines;

    @Column(name = "skip_empty_columns")
    private Boolean skipEmptyColumns;

    @Column(name = "fail_on_inconsistent_line_width")
    private Boolean failOnInconsistentLineWidth;

    @Column(name = "skip_ebcdic_header")
    private Boolean skipEbcdicHeader;

    @Column(name = "eol_present")
    private Boolean eolPresent;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @OneToMany(mappedBy = "fileConfig")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fileSource", "fileConfig" }, allowSetters = true)
    private Set<FileInfo> fileInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileConfig id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public FileConfig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public FileConfig detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getColumnNameLineNumber() {
        return this.columnNameLineNumber;
    }

    public FileConfig columnNameLineNumber(Integer columnNameLineNumber) {
        this.columnNameLineNumber = columnNameLineNumber;
        return this;
    }

    public void setColumnNameLineNumber(Integer columnNameLineNumber) {
        this.columnNameLineNumber = columnNameLineNumber;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public FileConfig encoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSeparatorChar() {
        return this.separatorChar;
    }

    public FileConfig separatorChar(String separatorChar) {
        this.separatorChar = separatorChar;
        return this;
    }

    public void setSeparatorChar(String separatorChar) {
        this.separatorChar = separatorChar;
    }

    public String getQuoteChar() {
        return this.quoteChar;
    }

    public FileConfig quoteChar(String quoteChar) {
        this.quoteChar = quoteChar;
        return this;
    }

    public void setQuoteChar(String quoteChar) {
        this.quoteChar = quoteChar;
    }

    public String getEscapeChar() {
        return this.escapeChar;
    }

    public FileConfig escapeChar(String escapeChar) {
        this.escapeChar = escapeChar;
        return this;
    }

    public void setEscapeChar(String escapeChar) {
        this.escapeChar = escapeChar;
    }

    public Integer getFixedValueWidth() {
        return this.fixedValueWidth;
    }

    public FileConfig fixedValueWidth(Integer fixedValueWidth) {
        this.fixedValueWidth = fixedValueWidth;
        return this;
    }

    public void setFixedValueWidth(Integer fixedValueWidth) {
        this.fixedValueWidth = fixedValueWidth;
    }

    public Boolean getSkipEmptyLines() {
        return this.skipEmptyLines;
    }

    public FileConfig skipEmptyLines(Boolean skipEmptyLines) {
        this.skipEmptyLines = skipEmptyLines;
        return this;
    }

    public void setSkipEmptyLines(Boolean skipEmptyLines) {
        this.skipEmptyLines = skipEmptyLines;
    }

    public Boolean getSkipEmptyColumns() {
        return this.skipEmptyColumns;
    }

    public FileConfig skipEmptyColumns(Boolean skipEmptyColumns) {
        this.skipEmptyColumns = skipEmptyColumns;
        return this;
    }

    public void setSkipEmptyColumns(Boolean skipEmptyColumns) {
        this.skipEmptyColumns = skipEmptyColumns;
    }

    public Boolean getFailOnInconsistentLineWidth() {
        return this.failOnInconsistentLineWidth;
    }

    public FileConfig failOnInconsistentLineWidth(Boolean failOnInconsistentLineWidth) {
        this.failOnInconsistentLineWidth = failOnInconsistentLineWidth;
        return this;
    }

    public void setFailOnInconsistentLineWidth(Boolean failOnInconsistentLineWidth) {
        this.failOnInconsistentLineWidth = failOnInconsistentLineWidth;
    }

    public Boolean getSkipEbcdicHeader() {
        return this.skipEbcdicHeader;
    }

    public FileConfig skipEbcdicHeader(Boolean skipEbcdicHeader) {
        this.skipEbcdicHeader = skipEbcdicHeader;
        return this;
    }

    public void setSkipEbcdicHeader(Boolean skipEbcdicHeader) {
        this.skipEbcdicHeader = skipEbcdicHeader;
    }

    public Boolean getEolPresent() {
        return this.eolPresent;
    }

    public FileConfig eolPresent(Boolean eolPresent) {
        this.eolPresent = eolPresent;
        return this;
    }

    public void setEolPresent(Boolean eolPresent) {
        this.eolPresent = eolPresent;
    }

    public String getFileType() {
        return this.fileType;
    }

    public FileConfig fileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public FileConfig creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public FileConfig lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<FileInfo> getFileInfos() {
        return this.fileInfos;
    }

    public FileConfig fileInfos(Set<FileInfo> fileInfos) {
        this.setFileInfos(fileInfos);
        return this;
    }

    public FileConfig addFileInfo(FileInfo fileInfo) {
        this.fileInfos.add(fileInfo);
        fileInfo.setFileConfig(this);
        return this;
    }

    public FileConfig removeFileInfo(FileInfo fileInfo) {
        this.fileInfos.remove(fileInfo);
        fileInfo.setFileConfig(null);
        return this;
    }

    public void setFileInfos(Set<FileInfo> fileInfos) {
        if (this.fileInfos != null) {
            this.fileInfos.forEach(i -> i.setFileConfig(null));
        }
        if (fileInfos != null) {
            fileInfos.forEach(i -> i.setFileConfig(this));
        }
        this.fileInfos = fileInfos;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileConfig)) {
            return false;
        }
        return id != null && id.equals(((FileConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileConfig{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", columnNameLineNumber=" + getColumnNameLineNumber() +
            ", encoding='" + getEncoding() + "'" +
            ", separatorChar='" + getSeparatorChar() + "'" +
            ", quoteChar='" + getQuoteChar() + "'" +
            ", escapeChar='" + getEscapeChar() + "'" +
            ", fixedValueWidth=" + getFixedValueWidth() +
            ", skipEmptyLines='" + getSkipEmptyLines() + "'" +
            ", skipEmptyColumns='" + getSkipEmptyColumns() + "'" +
            ", failOnInconsistentLineWidth='" + getFailOnInconsistentLineWidth() + "'" +
            ", skipEbcdicHeader='" + getSkipEbcdicHeader() + "'" +
            ", eolPresent='" + getEolPresent() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
