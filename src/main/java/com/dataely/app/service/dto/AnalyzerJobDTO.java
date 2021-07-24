package com.dataely.app.service.dto;

import com.dataely.app.domain.enumeration.EJobStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.AnalyzerJob} entity.
 */
public class AnalyzerJobDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String detail;

    private Instant endTime;

    private Instant startTime;

    private EJobStatus status;

    private String previousRunTime;

    private Instant creationDate;

    private Instant lastUpdated;

    private EnvironmentDTO environment;

    private DataSourceDTO dataSource;

    private FileSourceDTO fileSource;

    private UserDTO user;

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

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public EJobStatus getStatus() {
        return status;
    }

    public void setStatus(EJobStatus status) {
        this.status = status;
    }

    public String getPreviousRunTime() {
        return previousRunTime;
    }

    public void setPreviousRunTime(String previousRunTime) {
        this.previousRunTime = previousRunTime;
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

    public EnvironmentDTO getEnvironment() {
        return environment;
    }

    public void setEnvironment(EnvironmentDTO environment) {
        this.environment = environment;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalyzerJobDTO)) {
            return false;
        }

        AnalyzerJobDTO analyzerJobDTO = (AnalyzerJobDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, analyzerJobDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyzerJobDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", previousRunTime='" + getPreviousRunTime() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", environment=" + getEnvironment() +
            ", dataSource=" + getDataSource() +
            ", fileSource=" + getFileSource() +
            ", user=" + getUser() +
            "}";
    }
}
