package com.dataely.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.AnalyzerRecognizers} entity.
 */
public class AnalyzerRecognizersDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String detail;

    private String recognizerName;

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
        if (!(o instanceof AnalyzerRecognizersDTO)) {
            return false;
        }

        AnalyzerRecognizersDTO analyzerRecognizersDTO = (AnalyzerRecognizersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, analyzerRecognizersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyzerRecognizersDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", recognizerName='" + getRecognizerName() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
