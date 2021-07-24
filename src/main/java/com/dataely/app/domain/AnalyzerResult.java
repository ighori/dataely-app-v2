package com.dataely.app.domain;

import com.dataely.app.domain.enumeration.EAnalyzerObjectType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnalyzerResult.
 */
@Entity
@Table(name = "analyzer_result")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnalyzerResult implements Serializable {

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

    @Column(name = "object_id")
    private Integer objectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "object_type")
    private EAnalyzerObjectType objectType;

    @Column(name = "field_id")
    private Integer fieldId;

    @Column(name = "field_type")
    private String fieldType;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "start")
    private Integer start;

    @Column(name = "jhi_end")
    private Integer end;

    @Column(name = "score")
    private Float score;

    @Column(name = "recognizer")
    private String recognizer;

    @Column(name = "pattern_name")
    private String patternName;

    @Column(name = "pattern_expr")
    private String patternExpr;

    @Column(name = "original_score")
    private String originalScore;

    @Column(name = "textual_explanation")
    private String textualExplanation;

    @Column(name = "supportive_context_word")
    private String supportiveContextWord;

    @Column(name = "validation_result")
    private String validationResult;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "environment", "dsSchemas", "analyzerJobs", "analyzerResults" }, allowSetters = true)
    private DataSource dataSource;

    @ManyToOne
    @JsonIgnoreProperties(value = { "environment", "fileInfos", "analyzerJobs", "analyzerResults" }, allowSetters = true)
    private FileSource fileSource;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnalyzerResult id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AnalyzerResult name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public AnalyzerResult detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getObjectId() {
        return this.objectId;
    }

    public AnalyzerResult objectId(Integer objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public EAnalyzerObjectType getObjectType() {
        return this.objectType;
    }

    public AnalyzerResult objectType(EAnalyzerObjectType objectType) {
        this.objectType = objectType;
        return this;
    }

    public void setObjectType(EAnalyzerObjectType objectType) {
        this.objectType = objectType;
    }

    public Integer getFieldId() {
        return this.fieldId;
    }

    public AnalyzerResult fieldId(Integer fieldId) {
        this.fieldId = fieldId;
        return this;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public AnalyzerResult fieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public AnalyzerResult entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Integer getStart() {
        return this.start;
    }

    public AnalyzerResult start(Integer start) {
        this.start = start;
        return this;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return this.end;
    }

    public AnalyzerResult end(Integer end) {
        this.end = end;
        return this;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Float getScore() {
        return this.score;
    }

    public AnalyzerResult score(Float score) {
        this.score = score;
        return this;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getRecognizer() {
        return this.recognizer;
    }

    public AnalyzerResult recognizer(String recognizer) {
        this.recognizer = recognizer;
        return this;
    }

    public void setRecognizer(String recognizer) {
        this.recognizer = recognizer;
    }

    public String getPatternName() {
        return this.patternName;
    }

    public AnalyzerResult patternName(String patternName) {
        this.patternName = patternName;
        return this;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public String getPatternExpr() {
        return this.patternExpr;
    }

    public AnalyzerResult patternExpr(String patternExpr) {
        this.patternExpr = patternExpr;
        return this;
    }

    public void setPatternExpr(String patternExpr) {
        this.patternExpr = patternExpr;
    }

    public String getOriginalScore() {
        return this.originalScore;
    }

    public AnalyzerResult originalScore(String originalScore) {
        this.originalScore = originalScore;
        return this;
    }

    public void setOriginalScore(String originalScore) {
        this.originalScore = originalScore;
    }

    public String getTextualExplanation() {
        return this.textualExplanation;
    }

    public AnalyzerResult textualExplanation(String textualExplanation) {
        this.textualExplanation = textualExplanation;
        return this;
    }

    public void setTextualExplanation(String textualExplanation) {
        this.textualExplanation = textualExplanation;
    }

    public String getSupportiveContextWord() {
        return this.supportiveContextWord;
    }

    public AnalyzerResult supportiveContextWord(String supportiveContextWord) {
        this.supportiveContextWord = supportiveContextWord;
        return this;
    }

    public void setSupportiveContextWord(String supportiveContextWord) {
        this.supportiveContextWord = supportiveContextWord;
    }

    public String getValidationResult() {
        return this.validationResult;
    }

    public AnalyzerResult validationResult(String validationResult) {
        this.validationResult = validationResult;
        return this;
    }

    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public AnalyzerResult creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public AnalyzerResult lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public AnalyzerResult dataSource(DataSource dataSource) {
        this.setDataSource(dataSource);
        return this;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public FileSource getFileSource() {
        return this.fileSource;
    }

    public AnalyzerResult fileSource(FileSource fileSource) {
        this.setFileSource(fileSource);
        return this;
    }

    public void setFileSource(FileSource fileSource) {
        this.fileSource = fileSource;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalyzerResult)) {
            return false;
        }
        return id != null && id.equals(((AnalyzerResult) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyzerResult{" +
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
            "}";
    }
}
