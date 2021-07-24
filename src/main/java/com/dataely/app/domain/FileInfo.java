package com.dataely.app.domain;

import com.dataely.app.domain.enumeration.EdsType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FileInfo.
 */
@Entity
@Table(name = "file_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FileInfo implements Serializable {

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private EdsType fileType;

    @NotNull
    @Column(name = "file_path", nullable = false)
    private String filePath;

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

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "environment", "fileInfos", "analyzerJobs", "analyzerResults" }, allowSetters = true)
    private FileSource fileSource;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fileInfos" }, allowSetters = true)
    private FileConfig fileConfig;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileInfo id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public FileInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public FileInfo detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public EdsType getFileType() {
        return this.fileType;
    }

    public FileInfo fileType(EdsType fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(EdsType fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public FileInfo filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getColumnNameLineNumber() {
        return this.columnNameLineNumber;
    }

    public FileInfo columnNameLineNumber(Integer columnNameLineNumber) {
        this.columnNameLineNumber = columnNameLineNumber;
        return this;
    }

    public void setColumnNameLineNumber(Integer columnNameLineNumber) {
        this.columnNameLineNumber = columnNameLineNumber;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public FileInfo encoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSeparatorChar() {
        return this.separatorChar;
    }

    public FileInfo separatorChar(String separatorChar) {
        this.separatorChar = separatorChar;
        return this;
    }

    public void setSeparatorChar(String separatorChar) {
        this.separatorChar = separatorChar;
    }

    public String getQuoteChar() {
        return this.quoteChar;
    }

    public FileInfo quoteChar(String quoteChar) {
        this.quoteChar = quoteChar;
        return this;
    }

    public void setQuoteChar(String quoteChar) {
        this.quoteChar = quoteChar;
    }

    public String getEscapeChar() {
        return this.escapeChar;
    }

    public FileInfo escapeChar(String escapeChar) {
        this.escapeChar = escapeChar;
        return this;
    }

    public void setEscapeChar(String escapeChar) {
        this.escapeChar = escapeChar;
    }

    public Integer getFixedValueWidth() {
        return this.fixedValueWidth;
    }

    public FileInfo fixedValueWidth(Integer fixedValueWidth) {
        this.fixedValueWidth = fixedValueWidth;
        return this;
    }

    public void setFixedValueWidth(Integer fixedValueWidth) {
        this.fixedValueWidth = fixedValueWidth;
    }

    public Boolean getSkipEmptyLines() {
        return this.skipEmptyLines;
    }

    public FileInfo skipEmptyLines(Boolean skipEmptyLines) {
        this.skipEmptyLines = skipEmptyLines;
        return this;
    }

    public void setSkipEmptyLines(Boolean skipEmptyLines) {
        this.skipEmptyLines = skipEmptyLines;
    }

    public Boolean getSkipEmptyColumns() {
        return this.skipEmptyColumns;
    }

    public FileInfo skipEmptyColumns(Boolean skipEmptyColumns) {
        this.skipEmptyColumns = skipEmptyColumns;
        return this;
    }

    public void setSkipEmptyColumns(Boolean skipEmptyColumns) {
        this.skipEmptyColumns = skipEmptyColumns;
    }

    public Boolean getFailOnInconsistentLineWidth() {
        return this.failOnInconsistentLineWidth;
    }

    public FileInfo failOnInconsistentLineWidth(Boolean failOnInconsistentLineWidth) {
        this.failOnInconsistentLineWidth = failOnInconsistentLineWidth;
        return this;
    }

    public void setFailOnInconsistentLineWidth(Boolean failOnInconsistentLineWidth) {
        this.failOnInconsistentLineWidth = failOnInconsistentLineWidth;
    }

    public Boolean getSkipEbcdicHeader() {
        return this.skipEbcdicHeader;
    }

    public FileInfo skipEbcdicHeader(Boolean skipEbcdicHeader) {
        this.skipEbcdicHeader = skipEbcdicHeader;
        return this;
    }

    public void setSkipEbcdicHeader(Boolean skipEbcdicHeader) {
        this.skipEbcdicHeader = skipEbcdicHeader;
    }

    public Boolean getEolPresent() {
        return this.eolPresent;
    }

    public FileInfo eolPresent(Boolean eolPresent) {
        this.eolPresent = eolPresent;
        return this;
    }

    public void setEolPresent(Boolean eolPresent) {
        this.eolPresent = eolPresent;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public FileInfo creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public FileInfo lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public FileSource getFileSource() {
        return this.fileSource;
    }

    public FileInfo fileSource(FileSource fileSource) {
        this.setFileSource(fileSource);
        return this;
    }

    public void setFileSource(FileSource fileSource) {
        this.fileSource = fileSource;
    }

    public FileConfig getFileConfig() {
        return this.fileConfig;
    }

    public FileInfo fileConfig(FileConfig fileConfig) {
        this.setFileConfig(fileConfig);
        return this;
    }

    public void setFileConfig(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileInfo)) {
            return false;
        }
        return id != null && id.equals(((FileInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", filePath='" + getFilePath() + "'" +
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
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
