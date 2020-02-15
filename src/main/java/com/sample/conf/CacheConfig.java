package com.sample.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.CacheEventListenerFactoryConfiguration;
import net.sf.ehcache.event.CacheEventListenerAdapter;

@Configuration
public class CacheConfig {

  public static final String CACHE_NAME = "sampleCacheName";
  public static final int TTL_IN_SECONDS = 300;

  @Bean
  public CacheManager getCacheManager() {
    return CacheManager.getInstance();
  }  

  @Bean
  @Qualifier(CACHE_NAME)
  public Cache getCache() {
    CacheManager cm = getCacheManager();
    CacheEventListenerFactoryConfiguration eventListenerFactoryConfiguration =
        new CacheEventListenerFactoryConfiguration();
    eventListenerFactoryConfiguration.setProperties(
        "replicateAsynchronously=true, asynchronousReplicationIntervalMillis=1000, replicatePuts=true, replicateUpdates=true, replicateUpdatesViaCopy=false, replicateRemovals=true");
    CacheConfiguration config = new CacheConfiguration().name(CACHE_NAME).eternal(false)
        .maxEntriesLocalHeap(10000).timeToLiveSeconds(TTL_IN_SECONDS)
        .cacheEventListenerFactory(eventListenerFactoryConfiguration);
    cm.addCache(new Cache(config));
    Cache cache = cm.getCache(CACHE_NAME);
    cache.getCacheEventNotificationService().registerListener(new CacheEventListenerAdapter() {
      @Override
      public void notifyElementExpired(Ehcache ehCache, Element element) {
        cacheEvictionStarted(element);
      }
    });
    return cache;
  }

  private void cacheEvictionStarted(Element element) {
    System.out.println(Thread.currentThread().getName() + ":start evict " + element.getObjectKey().toString());    
  }
}
