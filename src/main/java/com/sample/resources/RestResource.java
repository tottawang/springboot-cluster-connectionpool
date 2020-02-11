package com.sample.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sample.conf.CacheConfig;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/api")
@Transactional(readOnly = true)
public class RestResource {

  @Autowired
  @Qualifier(CacheConfig.CACHE_NAME)
  private Cache cache;

  @Autowired
  private CacheOps cacheOps;

  @GET
  @Path("sample")
  public void getSample() throws InterruptedException {
    final String key = "key";
    final String value = "value";
    Element element = new Element(key, value, 0, CacheConfig.TTL_IN_SECONDS);

    cache.put(element);
    waitCacheExpired();
    cacheOps.evictNeverFinish();

    // make sure evict is in progress..
    // and its cache write lock won't be released due to notifyElementExpired call
    Thread.sleep(2000);

    // get call get stuck
    System.out.println("start get cache which is supposed to run into deadlock");
    cache.get(key);
    System.out.println("cache.get call should get stuck.. never return cache value");
  }

  private void waitCacheExpired() throws InterruptedException {
    Thread.sleep(CacheConfig.TTL_IN_SECONDS * 1000 + 1000);
  }

}
