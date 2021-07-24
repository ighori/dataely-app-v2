package com.dataely.app.domain;

import com.dataely.app.domain.enumeration.EDbType;
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
 * A DataSource.
 */
@Entity
@Table(name = "data_source")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataSource implements Serializable {

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
    @Column(name = "database_name", nullable = false)
    private String databaseName;

    @Column(name = "instance_name")
    private String instanceName;

    @Column(name = "schema_name")
    private String schemaName;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "db_type", nullable = false)
    private EDbType dbType;

    @Column(name = "db_version")
    private String dbVersion;

    @Column(name = "schema_count")
    private Integer schemaCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ds_type", nullable = false)
    private EdsType dsType;

    @Column(name = "driver_class_name")
    private String driverClassName;

    @Column(name = "jdbc_url")
    private String jdbcUrl;

    @Column(name = "sid")
    private String sid;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @ManyToOne
    @JsonIgnoreProperties(value = { "contact", "application", "dataSources", "fileSources", "analyzerJobs" }, allowSetters = true)
    private Environment environment;

    @OneToMany(mappedBy = "dataSource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dataSource", "dsSchemaRelationships", "tablesDefinitions" }, allowSetters = true)
    private Set<DsSchema> dsSchemas = new HashSet<>();

    @OneToMany(mappedBy = "dataSource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "environment", "dataSource", "fileSource", "user" }, allowSetters = true)
    private Set<AnalyzerJob> analyzerJobs = new HashSet<>();

    @OneToMany(mappedBy = "dataSource")
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

    public DataSource id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public DataSource name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public DataSource detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public DataSource databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    public DataSource instanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public DataSource schemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getHostname() {
        return this.hostname;
    }

    public DataSource hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return this.port;
    }

    public DataSource port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public DataSource username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public DataSource password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return this.icon;
    }

    public DataSource icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public EDbType getDbType() {
        return this.dbType;
    }

    public DataSource dbType(EDbType dbType) {
        this.dbType = dbType;
        return this;
    }

    public void setDbType(EDbType dbType) {
        this.dbType = dbType;
    }

    public String getDbVersion() {
        return this.dbVersion;
    }

    public DataSource dbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
        return this;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public Integer getSchemaCount() {
        return this.schemaCount;
    }

    public DataSource schemaCount(Integer schemaCount) {
        this.schemaCount = schemaCount;
        return this;
    }

    public void setSchemaCount(Integer schemaCount) {
        this.schemaCount = schemaCount;
    }

    public EdsType getDsType() {
        return this.dsType;
    }

    public DataSource dsType(EdsType dsType) {
        this.dsType = dsType;
        return this;
    }

    public void setDsType(EdsType dsType) {
        this.dsType = dsType;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public DataSource driverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public DataSource jdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
        return this;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getSid() {
        return this.sid;
    }

    public DataSource sid(String sid) {
        this.sid = sid;
        return this;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public DataSource creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public DataSource lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public DataSource environment(Environment environment) {
        this.setEnvironment(environment);
        return this;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Set<DsSchema> getDsSchemas() {
        return this.dsSchemas;
    }

    public DataSource dsSchemas(Set<DsSchema> dsSchemas) {
        this.setDsSchemas(dsSchemas);
        return this;
    }

    public DataSource addDsSchema(DsSchema dsSchema) {
        this.dsSchemas.add(dsSchema);
        dsSchema.setDataSource(this);
        return this;
    }

    public DataSource removeDsSchema(DsSchema dsSchema) {
        this.dsSchemas.remove(dsSchema);
        dsSchema.setDataSource(null);
        return this;
    }

    public void setDsSchemas(Set<DsSchema> dsSchemas) {
        if (this.dsSchemas != null) {
            this.dsSchemas.forEach(i -> i.setDataSource(null));
        }
        if (dsSchemas != null) {
            dsSchemas.forEach(i -> i.setDataSource(this));
        }
        this.dsSchemas = dsSchemas;
    }

    public Set<AnalyzerJob> getAnalyzerJobs() {
        return this.analyzerJobs;
    }

    public DataSource analyzerJobs(Set<AnalyzerJob> analyzerJobs) {
        this.setAnalyzerJobs(analyzerJobs);
        return this;
    }

    public DataSource addAnalyzerJob(AnalyzerJob analyzerJob) {
        this.analyzerJobs.add(analyzerJob);
        analyzerJob.setDataSource(this);
        return this;
    }

    public DataSource removeAnalyzerJob(AnalyzerJob analyzerJob) {
        this.analyzerJobs.remove(analyzerJob);
        analyzerJob.setDataSource(null);
        return this;
    }

    public void setAnalyzerJobs(Set<AnalyzerJob> analyzerJobs) {
        if (this.analyzerJobs != null) {
            this.analyzerJobs.forEach(i -> i.setDataSource(null));
        }
        if (analyzerJobs != null) {
            analyzerJobs.forEach(i -> i.setDataSource(this));
        }
        this.analyzerJobs = analyzerJobs;
    }

    public Set<AnalyzerResult> getAnalyzerResults() {
        return this.analyzerResults;
    }

    public DataSource analyzerResults(Set<AnalyzerResult> analyzerResults) {
        this.setAnalyzerResults(analyzerResults);
        return this;
    }

    public DataSource addAnalyzerResult(AnalyzerResult analyzerResult) {
        this.analyzerResults.add(analyzerResult);
        analyzerResult.setDataSource(this);
        return this;
    }

    public DataSource removeAnalyzerResult(AnalyzerResult analyzerResult) {
        this.analyzerResults.remove(analyzerResult);
        analyzerResult.setDataSource(null);
        return this;
    }

    public void setAnalyzerResults(Set<AnalyzerResult> analyzerResults) {
        if (this.analyzerResults != null) {
            this.analyzerResults.forEach(i -> i.setDataSource(null));
        }
        if (analyzerResults != null) {
            analyzerResults.forEach(i -> i.setDataSource(this));
        }
        this.analyzerResults = analyzerResults;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataSource)) {
            return false;
        }
        return id != null && id.equals(((DataSource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataSource{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", databaseName='" + getDatabaseName() + "'" +
            ", instanceName='" + getInstanceName() + "'" +
            ", schemaName='" + getSchemaName() + "'" +
            ", hostname='" + getHostname() + "'" +
            ", port=" + getPort() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", icon='" + getIcon() + "'" +
            ", dbType='" + getDbType() + "'" +
            ", dbVersion='" + getDbVersion() + "'" +
            ", schemaCount=" + getSchemaCount() +
            ", dsType='" + getDsType() + "'" +
            ", driverClassName='" + getDriverClassName() + "'" +
            ", jdbcUrl='" + getJdbcUrl() + "'" +
            ", sid='" + getSid() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
