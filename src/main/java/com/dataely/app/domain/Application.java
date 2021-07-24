package com.dataely.app.domain;

import com.dataely.app.domain.enumeration.EAppType;
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
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Application implements Serializable {

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

    @Column(name = "version")
    private String version;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "app_type", nullable = false)
    private EAppType appType;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contact", "application", "dataSources", "fileSources", "analyzerJobs" }, allowSetters = true)
    private Set<Environment> environments = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "applications", "organization" }, allowSetters = true)
    private BusinessUnit businessUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Application id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Application name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return this.detail;
    }

    public Application detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getVersion() {
        return this.version;
    }

    public Application version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public EAppType getAppType() {
        return this.appType;
    }

    public Application appType(EAppType appType) {
        this.appType = appType;
        return this;
    }

    public void setAppType(EAppType appType) {
        this.appType = appType;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public Application creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getLastUpdated() {
        return this.lastUpdated;
    }

    public Application lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Set<Environment> getEnvironments() {
        return this.environments;
    }

    public Application environments(Set<Environment> environments) {
        this.setEnvironments(environments);
        return this;
    }

    public Application addEnvironment(Environment environment) {
        this.environments.add(environment);
        environment.setApplication(this);
        return this;
    }

    public Application removeEnvironment(Environment environment) {
        this.environments.remove(environment);
        environment.setApplication(null);
        return this;
    }

    public void setEnvironments(Set<Environment> environments) {
        if (this.environments != null) {
            this.environments.forEach(i -> i.setApplication(null));
        }
        if (environments != null) {
            environments.forEach(i -> i.setApplication(this));
        }
        this.environments = environments;
    }

    public BusinessUnit getBusinessUnit() {
        return this.businessUnit;
    }

    public Application businessUnit(BusinessUnit businessUnit) {
        this.setBusinessUnit(businessUnit);
        return this;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", version='" + getVersion() + "'" +
            ", appType='" + getAppType() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
