package com.dataely.app.service.dto;

import com.dataely.app.domain.enumeration.EEnvPurpose;
import com.dataely.app.domain.enumeration.EEnvType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.dataely.app.domain.Environment} entity.
 */
public class EnvironmentDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String detail;

    @NotNull
    private EEnvType type;

    @NotNull
    private EEnvPurpose purpose;

    private Instant creationDate;

    private Instant lastUpdated;

    private ContactDTO contact;

    private ApplicationDTO application;

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

    public EEnvType getType() {
        return type;
    }

    public void setType(EEnvType type) {
        this.type = type;
    }

    public EEnvPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(EEnvPurpose purpose) {
        this.purpose = purpose;
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

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    public ApplicationDTO getApplication() {
        return application;
    }

    public void setApplication(ApplicationDTO application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnvironmentDTO)) {
            return false;
        }

        EnvironmentDTO environmentDTO = (EnvironmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, environmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnvironmentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", type='" + getType() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", contact=" + getContact() +
            ", application=" + getApplication() +
            "}";
    }
}
