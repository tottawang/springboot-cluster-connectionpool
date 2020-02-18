package com.sample.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sample.conf.EhCacheConfig;
import com.sample.service.TestLog;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Component
public class CacheOps {

  @Autowired
  @Qualifier(EhCacheConfig.CACHE_NAME)
  private Cache cache;

  public void putCache(String key, int value) {
    TestLog.log("add " + key + " to cache with value " + value);
    cache.put(new Element(key, value, true), false);
  }
}
