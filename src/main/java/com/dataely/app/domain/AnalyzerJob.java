package com.dataely.app.domain;

import com.dataely.app.domain.enumeration.EJobStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnalyzerJob.
 */
@Entity
@Table(name = "analyzer_job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AnalyzerJob implements Serializable {

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

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "start_time")
    private Instant startTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EJobStatus status;

    @Column(name = "previous_run_time")
    private String previousRunTime;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "contact", "application", "dataSources", "fileSources", "analyzerJobs" }, allowSetters = true)
    private Environment environment;

    @ManyToOne
    @JsonIgnoreProperties(value = { "environment", "dsSchemas", "analyzerJobs", "analyzerResults" }, allowSetters = true)
    private DataSource dataSource;

    @ManyToOne
    @JsonIgnoreProperties(value = { "environment", "fileInfos", "analyzerJobs", "analyzerResults" }, allowSetters = true)
    private FileSource fileSource;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnalyzerJob id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AnalyzerJob name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public AnalyzerJob detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public AnalyzerJob endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public AnalyzerJob startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public EJobStatus getStatus() {
        return this.status;
    }

    public AnalyzerJob status(EJobStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EJobStatus status) {
        this.status = status;
    }

    public String getPreviousRunTime() {
        return this.previousRunTime;
    }

    public AnalyzerJob previousRunTime(String previousRunTime) {
        this.previousRunTime = previousRunTime;
        return this;
    }

    public void setPreviousRunTime(String previousRunTime) {
        this.previousRunTime = previousRunTime;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public AnalyzerJob creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public AnalyzerJob lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public AnalyzerJob environment(Environment environment) {
        this.setEnvironment(environment);
        return this;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public AnalyzerJob dataSource(DataSource dataSource) {
        this.setDataSource(dataSource);
        return this;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public FileSource getFileSource() {
        return this.fileSource;
    }

    public AnalyzerJob fileSource(FileSource fileSource) {
        this.setFileSource(fileSource);
        return this;
    }

    public void setFileSource(FileSource fileSource) {
        this.fileSource = fileSource;
    }

    public User getUser() {
        return this.user;
    }

    public AnalyzerJob user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalyzerJob)) {
            return false;
        }
        return id != null && id.equals(((AnalyzerJob) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalyzerJob{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", previousRunTime='" + getPreviousRunTime() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
