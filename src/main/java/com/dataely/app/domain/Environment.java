package com.dataely.app.domain;

import com.dataely.app.domain.enumeration.EEnvPurpose;
import com.dataely.app.domain.enumeration.EEnvType;
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
 * A Environment.
 */
@Entity
@Table(name = "environment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Environment implements Serializable {

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
    @Column(name = "type", nullable = false)
    private EEnvType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", nullable = false)
    private EEnvPurpose purpose;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "environments" }, allowSetters = true)
    private Contact contact;

    @ManyToOne
    @JsonIgnoreProperties(value = { "environments", "businessUnit" }, allowSetters = true)
    private Application application;

    @OneToMany(mappedBy = "environment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "environment", "dsSchemas", "analyzerJobs", "analyzerResults" }, allowSetters = true)
    private Set<DataSource> dataSources = new HashSet<>();

    @OneToMany(mappedBy = "environment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "environment", "fileInfos", "analyzerJobs", "analyzerResults" }, allowSetters = true)
    private Set<FileSource> fileSources = new HashSet<>();

    @OneToMany(mappedBy = "environment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "environment", "dataSource", "fileSource", "user" }, allowSetters = true)
    private Set<AnalyzerJob> analyzerJobs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Environment id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Environment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public Environment detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public EEnvType getType() {
        return this.type;
    }

    public Environment type(EEnvType type) {
        this.type = type;
        return this;
    }

    public void setType(EEnvType type) {
        this.type = type;
    }

    public EEnvPurpose getPurpose() {
        return this.purpose;
    }

    public Environment purpose(EEnvPurpose purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(EEnvPurpose purpose) {
        this.purpose = purpose;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public Environment creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public Environment lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Contact getContact() {
        return this.contact;
    }

    public Environment contact(Contact contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Application getApplication() {
        return this.application;
    }

    public Environment application(Application application) {
        this.setApplication(application);
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Set<DataSource> getDataSources() {
        return this.dataSources;
    }

    public Environment dataSources(Set<DataSource> dataSources) {
        this.setDataSources(dataSources);
        return this;
    }

    public Environment addDataSource(DataSource dataSource) {
        this.dataSources.add(dataSource);
        dataSource.setEnvironment(this);
        return this;
    }

    public Environment removeDataSource(DataSource dataSource) {
        this.dataSources.remove(dataSource);
        dataSource.setEnvironment(null);
        return this;
    }

    public void setDataSources(Set<DataSource> dataSources) {
        if (this.dataSources != null) {
            this.dataSources.forEach(i -> i.setEnvironment(null));
        }
        if (dataSources != null) {
            dataSources.forEach(i -> i.setEnvironment(this));
        }
        this.dataSources = dataSources;
    }

    public Set<FileSource> getFileSources() {
        return this.fileSources;
    }

    public Environment fileSources(Set<FileSource> fileSources) {
        this.setFileSources(fileSources);
        return this;
    }

    public Environment addFileSource(FileSource fileSource) {
        this.fileSources.add(fileSource);
        fileSource.setEnvironment(this);
        return this;
    }

    public Environment removeFileSource(FileSource fileSource) {
        this.fileSources.remove(fileSource);
        fileSource.setEnvironment(null);
        return this;
    }

    public void setFileSources(Set<FileSource> fileSources) {
        if (this.fileSources != null) {
            this.fileSources.forEach(i -> i.setEnvironment(null));
        }
        if (fileSources != null) {
            fileSources.forEach(i -> i.setEnvironment(this));
        }
        this.fileSources = fileSources;
    }

    public Set<AnalyzerJob> getAnalyzerJobs() {
        return this.analyzerJobs;
    }

    public Environment analyzerJobs(Set<AnalyzerJob> analyzerJobs) {
        this.setAnalyzerJobs(analyzerJobs);
        return this;
    }

    public Environment addAnalyzerJob(AnalyzerJob analyzerJob) {
        this.analyzerJobs.add(analyzerJob);
        analyzerJob.setEnvironment(this);
        return this;
    }

    public Environment removeAnalyzerJob(AnalyzerJob analyzerJob) {
        this.analyzerJobs.remove(analyzerJob);
        analyzerJob.setEnvironment(null);
        return this;
    }

    public void setAnalyzerJobs(Set<AnalyzerJob> analyzerJobs) {
        if (this.analyzerJobs != null) {
            this.analyzerJobs.forEach(i -> i.setEnvironment(null));
        }
        if (analyzerJobs != null) {
            analyzerJobs.forEach(i -> i.setEnvironment(this));
        }
        this.analyzerJobs = analyzerJobs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Environment)) {
            return false;
        }
        return id != null && id.equals(((Environment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Environment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", type='" + getType() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
