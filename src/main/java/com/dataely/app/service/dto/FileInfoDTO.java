package com.dataely.app.service.dto;

import com.dataely.app.domain.enumeration.EdsType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.FileInfo} entity.
 */
public class FileInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String detail;

    @NotNull
    private EdsType fileType;

    @NotNull
    private String filePath;

    private Integer columnNameLineNumber;

    private String encoding;

    private String separatorChar;

    private String quoteChar;

    private String escapeChar;

    private Integer fixedValueWidth;

    private Boolean skipEmptyLines;

    private Boolean skipEmptyColumns;

    private Boolean failOnInconsistentLineWidth;

    private Boolean skipEbcdicHeader;

    private Boolean eolPresent;

    private Instant creationDate;

    private Instant lastUpdated;

    private FileSourceDTO fileSource;

    private FileConfigDTO fileConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public EdsType getFileType() {
        return fileType;
    }

    public void setFileType(EdsType fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getColumnNameLineNumber() {
        return columnNameLineNumber;
    }

    public void setColumnNameLineNumber(Integer columnNameLineNumber) {
        this.columnNameLineNumber = columnNameLineNumber;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSeparatorChar() {
        return separatorChar;
    }

    public void setSeparatorChar(String separatorChar) {
        this.separatorChar = separatorChar;
    }

    public String getQuoteChar() {
        return quoteChar;
    }

    public void setQuoteChar(String quoteChar) {
        this.quoteChar = quoteChar;
    }

    public String getEscapeChar() {
        return escapeChar;
    }

    public void setEscapeChar(String escapeChar) {
        this.escapeChar = escapeChar;
    }

    public Integer getFixedValueWidth() {
        return fixedValueWidth;
    }

    public void setFixedValueWidth(Integer fixedValueWidth) {
        this.fixedValueWidth = fixedValueWidth;
    }

    public Boolean getSkipEmptyLines() {
        return skipEmptyLines;
    }

    public void setSkipEmptyLines(Boolean skipEmptyLines) {
        this.skipEmptyLines = skipEmptyLines;
    }

    public Boolean getSkipEmptyColumns() {
        return skipEmptyColumns;
    }

    public void setSkipEmptyColumns(Boolean skipEmptyColumns) {
        this.skipEmptyColumns = skipEmptyColumns;
    }

    public Boolean getFailOnInconsistentLineWidth() {
        return failOnInconsistentLineWidth;
    }

    public void setFailOnInconsistentLineWidth(Boolean failOnInconsistentLineWidth) {
        this.failOnInconsistentLineWidth = failOnInconsistentLineWidth;
    }

    public Boolean getSkipEbcdicHeader() {
        return skipEbcdicHeader;
    }

    public void setSkipEbcdicHeader(Boolean skipEbcdicHeader) {
        this.skipEbcdicHeader = skipEbcdicHeader;
    }

    public Boolean getEolPresent() {
        return eolPresent;
    }

    public void setEolPresent(Boolean eolPresent) {
        this.eolPresent = eolPresent;
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

    public FileSourceDTO getFileSource() {
        return fileSource;
    }

    public void setFileSource(FileSourceDTO fileSource) {
        this.fileSource = fileSource;
    }

    public FileConfigDTO getFileConfig() {
        return fileConfig;
    }

    public void setFileConfig(FileConfigDTO fileConfig) {
        this.fileConfig = fileConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileInfoDTO)) {
            return false;
        }

        FileInfoDTO fileInfoDTO = (FileInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileInfoDTO{" +
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
            ", fileSource=" + getFileSource() +
            ", fileConfig=" + getFileConfig() +
            "}";
    }
}
