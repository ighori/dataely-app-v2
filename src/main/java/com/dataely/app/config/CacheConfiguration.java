package com.dataely.app.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.dataely.app.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.dataely.app.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.dataely.app.domain.User.class.getName());
            createCache(cm, com.dataely.app.domain.Authority.class.getName());
            createCache(cm, com.dataely.app.domain.User.class.getName() + ".authorities");
            createCache(cm, com.dataely.app.domain.Organization.class.getName());
            createCache(cm, com.dataely.app.domain.Organization.class.getName() + ".businessUnits");
            createCache(cm, com.dataely.app.domain.BusinessUnit.class.getName());
            createCache(cm, com.dataely.app.domain.BusinessUnit.class.getName() + ".applications");
            createCache(cm, com.dataely.app.domain.Application.class.getName());
            createCache(cm, com.dataely.app.domain.Application.class.getName() + ".environments");
            createCache(cm, com.dataely.app.domain.Contact.class.getName());
            createCache(cm, com.dataely.app.domain.Contact.class.getName() + ".environments");
            createCache(cm, com.dataely.app.domain.Environment.class.getName());
            createCache(cm, com.dataely.app.domain.Environment.class.getName() + ".dataSources");
            createCache(cm, com.dataely.app.domain.Environment.class.getName() + ".fileSources");
            createCache(cm, com.dataely.app.domain.Environment.class.getName() + ".analyzerJobs");
            createCache(cm, com.dataely.app.domain.DataSource.class.getName());
            createCache(cm, com.dataely.app.domain.DataSource.class.getName() + ".dsSchemas");
            createCache(cm, com.dataely.app.domain.DataSource.class.getName() + ".analyzerJobs");
            createCache(cm, com.dataely.app.domain.DataSource.class.getName() + ".analyzerResults");
            createCache(cm, com.dataely.app.domain.DsSchema.class.getName());
            createCache(cm, com.dataely.app.domain.DsSchema.class.getName() + ".dsSchemaRelationships");
            createCache(cm, com.dataely.app.domain.DsSchema.class.getName() + ".tablesDefinitions");
            createCache(cm, com.dataely.app.domain.DsSchemaRelationship.class.getName());
            createCache(cm, com.dataely.app.domain.TablesDefinition.class.getName());
            createCache(cm, com.dataely.app.domain.TablesDefinition.class.getName() + ".tableColumns");
            createCache(cm, com.dataely.app.domain.TableColumn.class.getName());
            createCache(cm, com.dataely.app.domain.TableRelationship.class.getName());
            createCache(cm, com.dataely.app.domain.RelatedTable.class.getName());
            createCache(cm, com.dataely.app.domain.RelatedTableColumn.class.getName());
            createCache(cm, com.dataely.app.domain.FileSource.class.getName());
            createCache(cm, com.dataely.app.domain.FileSource.class.getName() + ".fileInfos");
            createCache(cm, com.dataely.app.domain.FileSource.class.getName() + ".analyzerJobs");
            createCache(cm, com.dataely.app.domain.FileSource.class.getName() + ".analyzerResults");
            createCache(cm, com.dataely.app.domain.FileConfig.class.getName());
            createCache(cm, com.dataely.app.domain.FileConfig.class.getName() + ".fileInfos");
            createCache(cm, com.dataely.app.domain.FileInfo.class.getName());
            createCache(cm, com.dataely.app.domain.FileField.class.getName());
            createCache(cm, com.dataely.app.domain.AnalyzerEntities.class.getName());
            createCache(cm, com.dataely.app.domain.AnalyzerRecognizers.class.getName());
            createCache(cm, com.dataely.app.domain.AnalyzerAdHocRecognizers.class.getName());
            createCache(cm, com.dataely.app.domain.AnalyzerResult.class.getName());
            createCache(cm, com.dataely.app.domain.AnalyzerJob.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
