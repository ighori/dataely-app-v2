package com.dataely.app.domain;

import com.dataely.app.domain.enumeration.EdsType;
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
 * A FileSource.
 */
@Entity
@Table(name = "file_source")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FileSource implements Serializable {

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
    @Column(name = "hostname", nullable = false)
    private String hostname;

    @NotNull
    @Column(name = "port", nullable = false)
    private Integer port;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "icon")
    private String icon;

    @Column(name = "connection_type")
    private String connectionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EdsType type;

    @Column(name = "region")
    private String region;

    @Column(name = "ignore_dotted_files")
    private Boolean ignoreDottedFiles;

    @Column(name = "recurse")
    private Boolean recurse;

    @Column(name = "path_filter_regex")
    private String pathFilterRegex;

    @Column(name = "remote_path")
    private String remotePath;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "contact", "application", "dataSources", "fileSources", "analyzerJobs" }, allowSetters = true)
    private Environment environment;

    @OneToMany(mappedBy = "fileSource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fileSource", "fileConfig" }, allowSetters = true)
    private Set<FileInfo> fileInfos = new HashSet<>();

    @OneToMany(mappedBy = "fileSource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "environment", "dataSource", "fileSource", "user" }, allowSetters = true)
    private Set<AnalyzerJob> analyzerJobs = new HashSet<>();

    @OneToMany(mappedBy = "fileSource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dataSource", "fileSource" }, allowSetters = true)
    private Set<AnalyzerResult> analyzerResults = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileSource id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public FileSource name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public FileSource detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getHostname() {
        return this.hostname;
    }

    public FileSource hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return this.port;
    }

    public FileSource port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public FileSource username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public FileSource password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return this.icon;
    }

    public FileSource icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getConnectionType() {
        return this.connectionType;
    }

    public FileSource connectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public EdsType getType() {
        return this.type;
    }

    public FileSource type(EdsType type) {
        this.type = type;
        return this;
    }

    public void setType(EdsType type) {
        this.type = type;
    }

    public String getRegion() {
        return this.region;
    }

    public FileSource region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getIgnoreDottedFiles() {
        return this.ignoreDottedFiles;
    }

    public FileSource ignoreDottedFiles(Boolean ignoreDottedFiles) {
        this.ignoreDottedFiles = ignoreDottedFiles;
        return this;
    }

    public void setIgnoreDottedFiles(Boolean ignoreDottedFiles) {
        this.ignoreDottedFiles = ignoreDottedFiles;
    }

    public Boolean getRecurse() {
        return this.recurse;
    }

    public FileSource recurse(Boolean recurse) {
        this.recurse = recurse;
        return this;
    }

    public void setRecurse(Boolean recurse) {
        this.recurse = recurse;
    }

    public String getPathFilterRegex() {
        return this.pathFilterRegex;
    }

    public FileSource pathFilterRegex(String pathFilterRegex) {
        this.pathFilterRegex = pathFilterRegex;
        return this;
    }

    public void setPathFilterRegex(String pathFilterRegex) {
        this.pathFilterRegex = pathFilterRegex;
    }

    public String getRemotePath() {
        return this.remotePath;
    }

    public FileSource remotePath(String remotePath) {
        this.remotePath = remotePath;
        return this;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public FileSource creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public FileSource lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public FileSource environment(Environment environment) {
        this.setEnvironment(environment);
        return this;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Set<FileInfo> getFileInfos() {
        return this.fileInfos;
    }

    public FileSource fileInfos(Set<FileInfo> fileInfos) {
        this.setFileInfos(fileInfos);
        return this;
    }

    public FileSource addFileInfo(FileInfo fileInfo) {
        this.fileInfos.add(fileInfo);
        fileInfo.setFileSource(this);
        return this;
    }

    public FileSource removeFileInfo(FileInfo fileInfo) {
        this.fileInfos.remove(fileInfo);
        fileInfo.setFileSource(null);
        return this;
    }

    public void setFileInfos(Set<FileInfo> fileInfos) {
        if (this.fileInfos != null) {
            this.fileInfos.forEach(i -> i.setFileSource(null));
        }
        if (fileInfos != null) {
            fileInfos.forEach(i -> i.setFileSource(this));
        }
        this.fileInfos = fileInfos;
    }

    public Set<AnalyzerJob> getAnalyzerJobs() {
        return this.analyzerJobs;
    }

    public FileSource analyzerJobs(Set<AnalyzerJob> analyzerJobs) {
        this.setAnalyzerJobs(analyzerJobs);
        return this;
    }

    public FileSource addAnalyzerJob(AnalyzerJob analyzerJob) {
        this.analyzerJobs.add(analyzerJob);
        analyzerJob.setFileSource(this);
        return this;
    }

    public FileSource removeAnalyzerJob(AnalyzerJob analyzerJob) {
        this.analyzerJobs.remove(analyzerJob);
        analyzerJob.setFileSource(null);
        return this;
    }

    public void setAnalyzerJobs(Set<AnalyzerJob> analyzerJobs) {
        if (this.analyzerJobs != null) {
            this.analyzerJobs.forEach(i -> i.setFileSource(null));
        }
        if (analyzerJobs != null) {
            analyzerJobs.forEach(i -> i.setFileSource(this));
        }
        this.analyzerJobs = analyzerJobs;
    }

    public Set<AnalyzerResult> getAnalyzerResults() {
        return this.analyzerResults;
    }

    public FileSource analyzerResults(Set<AnalyzerResult> analyzerResults) {
        this.setAnalyzerResults(analyzerResults);
        return this;
    }

    public FileSource addAnalyzerResult(AnalyzerResult analyzerResult) {
        this.analyzerResults.add(analyzerResult);
        analyzerResult.setFileSource(this);
        return this;
    }

    public FileSource removeAnalyzerResult(AnalyzerResult analyzerResult) {
        this.analyzerResults.remove(analyzerResult);
        analyzerResult.setFileSource(null);
        return this;
    }

    public void setAnalyzerResults(Set<AnalyzerResult> analyzerResults) {
        if (this.analyzerResults != null) {
            this.analyzerResults.forEach(i -> i.setFileSource(null));
        }
        if (analyzerResults != null) {
            analyzerResults.forEach(i -> i.setFileSource(this));
        }
        this.analyzerResults = analyzerResults;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileSource)) {
            return false;
        }
        return id != null && id.equals(((FileSource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileSource{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", hostname='" + getHostname() + "'" +
            ", port=" + getPort() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", icon='" + getIcon() + "'" +
            ", connectionType='" + getConnectionType() + "'" +
            ", type='" + getType() + "'" +
            ", region='" + getRegion() + "'" +
            ", ignoreDottedFiles='" + getIgnoreDottedFiles() + "'" +
            ", recurse='" + getRecurse() + "'" +
            ", pathFilterRegex='" + getPathFilterRegex() + "'" +
            ", remotePath='" + getRemotePath() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
