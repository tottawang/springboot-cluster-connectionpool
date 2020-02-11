package com.sample.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.sample.conf.CacheConfig;

import net.sf.ehcache.Cache;

@Component
public class CacheOps {

  @Autowired
  @Qualifier(CacheConfig.CACHE_NAME)
  private Cache cache;

  @Async
  public void evictNeverFinish() {
    System.out.println(Thread.currentThread().getName() + ":start evict..");
    cache.getAll(cache.getKeysWithExpiryCheck()).values()
        .forEach(t -> System.out.println("get cache: " + t.toString()));
  }

}
