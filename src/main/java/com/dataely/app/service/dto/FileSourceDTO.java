package com.dataely.app.service.dto;

import com.dataely.app.domain.enumeration.EdsType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.FileSource} entity.
 */
public class FileSourceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String detail;

    @NotNull
    private String hostname;

    @NotNull
    private Integer port;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String icon;

    private String connectionType;

    private EdsType type;

    private String region;

    private Boolean ignoreDottedFiles;

    private Boolean recurse;

    private String pathFilterRegex;

    private String remotePath;

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

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public EdsType getType() {
        return type;
    }

    public void setType(EdsType type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getIgnoreDottedFiles() {
        return ignoreDottedFiles;
    }

    public void setIgnoreDottedFiles(Boolean ignoreDottedFiles) {
        this.ignoreDottedFiles = ignoreDottedFiles;
    }

    public Boolean getRecurse() {
        return recurse;
    }

    public void setRecurse(Boolean recurse) {
        this.recurse = recurse;
    }

    public String getPathFilterRegex() {
        return pathFilterRegex;
    }

    public void setPathFilterRegex(String pathFilterRegex) {
        this.pathFilterRegex = pathFilterRegex;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
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
        if (!(o instanceof FileSourceDTO)) {
            return false;
        }

        FileSourceDTO fileSourceDTO = (FileSourceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileSourceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileSourceDTO{" +
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
            ", environment=" + getEnvironment() +
            "}";
    }
}
