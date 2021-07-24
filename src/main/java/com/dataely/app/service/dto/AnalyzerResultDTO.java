package com.dataely.app.service.dto;

import com.dataely.app.domain.enumeration.EAnalyzerObjectType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.AnalyzerResult} entity.
 */
public class AnalyzerResultDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String detail;

    private Integer objectId;

    private EAnalyzerObjectType objectType;

    private Integer fieldId;

    private String fieldType;

    private String entityType;

    private Integer start;

    private Integer end;

    private Float score;

    private String recognizer;

    private String patternName;

    private String patternExpr;

    private String originalScore;

    private String textualExplanation;

    private String supportiveContextWord;

    private String validationResult;

    private Instant creationDate;

    private Instant lastUpdated;

    private DataSourceDTO dataSource;

    private FileSourceDTO fileSource;

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

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public EAnalyzerObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(EAnalyzerObjectType objectType) {
        this.objectType = objectType;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getRecognizer() {
        return recognizer;
    }

    public void setRecognizer(String recognizer) {
        this.recognizer = recognizer;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public String getPatternExpr() {
        return patternExpr;
    }

    public void setPatternExpr(String patternExpr) {
        this.patternExpr = patternExpr;
    }

    public String getOriginalScore() {
        return originalScore;
    }

    public void setOriginalScore(String originalScore) {
        this.originalScore = originalScore;
    }

    public String getTextualExplanation() {
        return textualExplanation;
    }

    public void setTextualExplanation(String textualExplanation) {
        this.textualExplanation = textualExplanation;
    }

    public String getSupportiveContextWord() {
        return supportiveContextWord;
    }

    public void setSupportiveContextWord(String supportiveContextWord) {
        this.supportiveContextWord = supportiveContextWord;
    }

    public String getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
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

    public DataSourceDTO getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSourceDTO dataSource) {
        this.dataSource = dataSource;
    }

    public FileSourceDTO getFileSource() {
        return fileSource;
    }

    public void setFileSource(FileSourceDTO fileSource) {
        this.fileSource = fileSource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalyzerResultDTO)) {
            return false;
        }

        AnalyzerResultDTO analyzerResultDTO = (AnalyzerResultDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, analyzerResultDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyzerResultDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", objectId=" + getObjectId() +
            ", objectType='" + getObjectType() + "'" +
            ", fieldId=" + getFieldId() +
            ", fieldType='" + getFieldType() + "'" +
            ", entityType='" + getEntityType() + "'" +
            ", start=" + getStart() +
            ", end=" + getEnd() +
            ", score=" + getScore() +
            ", recognizer='" + getRecognizer() + "'" +
            ", patternName='" + getPatternName() + "'" +
            ", patternExpr='" + getPatternExpr() + "'" +
            ", originalScore='" + getOriginalScore() + "'" +
            ", textualExplanation='" + getTextualExplanation() + "'" +
            ", supportiveContextWord='" + getSupportiveContextWord() + "'" +
            ", validationResult='" + getValidationResult() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", dataSource=" + getDataSource() +
            ", fileSource=" + getFileSource() +
            "}";
    }
}
