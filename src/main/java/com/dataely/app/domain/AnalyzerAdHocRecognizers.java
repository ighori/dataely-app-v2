package com.dataely.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnalyzerAdHocRecognizers.
 */
@Entity
@Table(name = "analyzer_ad_hoc_recognizers")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnalyzerAdHocRecognizers implements Serializable {

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

    @Column(name = "recognizer_name")
    private String recognizerName;

    @Column(name = "supported_lang")
    private String supportedLang;

    @Column(name = "pattern_name")
    private String patternName;

    @Column(name = "regex")
    private String regex;

    @Column(name = "score")
    private Float score;

    @Column(name = "context")
    private String context;

    @Column(name = "deny_list")
    private String denyList;

    @Column(name = "supported_entity")
    private String supportedEntity;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnalyzerAdHocRecognizers id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AnalyzerAdHocRecognizers name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public AnalyzerAdHocRecognizers detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRecognizerName() {
        return this.recognizerName;
    }

    public AnalyzerAdHocRecognizers recognizerName(String recognizerName) {
        this.recognizerName = recognizerName;
        return this;
    }

    public void setRecognizerName(String recognizerName) {
        this.recognizerName = recognizerName;
    }

    public String getSupportedLang() {
        return this.supportedLang;
    }

    public AnalyzerAdHocRecognizers supportedLang(String supportedLang) {
        this.supportedLang = supportedLang;
        return this;
    }

    public void setSupportedLang(String supportedLang) {
        this.supportedLang = supportedLang;
    }

    public String getPatternName() {
        return this.patternName;
    }

    public AnalyzerAdHocRecognizers patternName(String patternName) {
        this.patternName = patternName;
        return this;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public String getRegex() {
        return this.regex;
    }

    public AnalyzerAdHocRecognizers regex(String regex) {
        this.regex = regex;
        return this;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Float getScore() {
        return this.score;
    }

    public AnalyzerAdHocRecognizers score(Float score) {
        this.score = score;
        return this;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getContext() {
        return this.context;
    }

    public AnalyzerAdHocRecognizers context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDenyList() {
        return this.denyList;
    }

    public AnalyzerAdHocRecognizers denyList(String denyList) {
        this.denyList = denyList;
        return this;
    }

    public void setDenyList(String denyList) {
        this.denyList = denyList;
    }

    public String getSupportedEntity() {
        return this.supportedEntity;
    }

    public AnalyzerAdHocRecognizers supportedEntity(String supportedEntity) {
        this.supportedEntity = supportedEntity;
        return this;
    }

    public void setSupportedEntity(String supportedEntity) {
        this.supportedEntity = supportedEntity;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public AnalyzerAdHocRecognizers creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public AnalyzerAdHocRecognizers lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalyzerAdHocRecognizers)) {
            return false;
        }
        return id != null && id.equals(((AnalyzerAdHocRecognizers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyzerAdHocRecognizers{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", recognizerName='" + getRecognizerName() + "'" +
            ", supportedLang='" + getSupportedLang() + "'" +
            ", patternName='" + getPatternName() + "'" +
            ", regex='" + getRegex() + "'" +
            ", score=" + getScore() +
            ", context='" + getContext() + "'" +
            ", denyList='" + getDenyList() + "'" +
            ", supportedEntity='" + getSupportedEntity() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
