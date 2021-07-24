package com.dataely.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.AnalyzerAdHocRecognizers} entity.
 */
public class AnalyzerAdHocRecognizersDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String detail;

    private String recognizerName;

    private String supportedLang;

    private String patternName;

    private String regex;

    private Float score;

    private String context;

    private String denyList;

    private String supportedEntity;

    private Instant creationDate;

    private Instant lastUpdated;

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

    public String getRecognizerName() {
        return recognizerName;
    }

    public void setRecognizerName(String recognizerName) {
        this.recognizerName = recognizerName;
    }

    public String getSupportedLang() {
        return supportedLang;
    }

    public void setSupportedLang(String supportedLang) {
        this.supportedLang = supportedLang;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDenyList() {
        return denyList;
    }

    public void setDenyList(String denyList) {
        this.denyList = denyList;
    }

    public String getSupportedEntity() {
        return supportedEntity;
    }

    public void setSupportedEntity(String supportedEntity) {
        this.supportedEntity = supportedEntity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalyzerAdHocRecognizersDTO)) {
            return false;
        }

        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = (AnalyzerAdHocRecognizersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, analyzerAdHocRecognizersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyzerAdHocRecognizersDTO{" +
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
