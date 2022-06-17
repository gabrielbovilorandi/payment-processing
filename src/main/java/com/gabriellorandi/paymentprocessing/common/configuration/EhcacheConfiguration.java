package com.gabriellorandi.paymentprocessing.common.configuration;

import com.gabriellorandi.paymentprocessing.operationtype.domain.OperationType;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
public class EhcacheConfiguration {

    @Value("${cache.expiration-time:60}")
    private int expirationTime;

    @Bean
    public CacheManager EhcacheManager() {

        ResourcePoolsBuilder.newResourcePoolsBuilder();
        CacheConfiguration<Integer, OperationType> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Integer.class,
                        OperationType.class,
                        ResourcePoolsBuilder
                                .heap(10)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(expirationTime)))
                .build();

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        javax.cache.configuration.Configuration<Integer, OperationType> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig);
        cacheManager.createCache("cacheStore", configuration);
        return cacheManager;
    }

}
