package com.dataely.app.service.dto;

import com.dataely.app.domain.enumeration.EDbType;
import com.dataely.app.domain.enumeration.EdsType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.DataSource} entity.
 */
public class DataSourceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String detail;

    @NotNull
    private String databaseName;

    private String instanceName;

    private String schemaName;

    @NotNull
    private String hostname;

    @NotNull
    private Integer port;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String icon;

    @NotNull
    private EDbType dbType;

    private String dbVersion;

    private Integer schemaCount;

    @NotNull
    private EdsType dsType;

    private String driverClassName;

    private String jdbcUrl;

    private String sid;

    private Instant creationDate;

    private Instant lastUpdated;

    private EnvironmentDTO environment;

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

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public EDbType getDbType() {
        return dbType;
    }

    public void setDbType(EDbType dbType) {
        this.dbType = dbType;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public Integer getSchemaCount() {
        return schemaCount;
    }

    public void setSchemaCount(Integer schemaCount) {
        this.schemaCount = schemaCount;
    }

    public EdsType getDsType() {
        return dsType;
    }

    public void setDsType(EdsType dsType) {
        this.dsType = dsType;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataSourceDTO)) {
            return false;
        }

        DataSourceDTO dataSourceDTO = (DataSourceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dataSourceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataSourceDTO{" +
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
            ", environment=" + getEnvironment() +
            "}";
    }
}
