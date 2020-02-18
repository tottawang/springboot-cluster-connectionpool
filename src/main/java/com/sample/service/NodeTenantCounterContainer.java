package com.sample.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sample.resources.CacheOps;

@Component
public class NodeTenantCounterContainer {

  private Map<NodeTenantKey, NodeTenantCounter> nodeTenantCounters;

  @Autowired
  private CacheOps cacheOps;

  @PostConstruct
  public void init() {
    nodeTenantCounters = new HashMap<>();
    addNodes();
  }

  public void addNodes() {
    String tenant = "myTestTenant";
    String node = "myTestNode";
    NodeTenantCounter counter = new NodeTenantCounter(node, tenant, cacheOps);
    nodeTenantCounters.put(counter.getKey(), counter);
  }

  public NodeTenantCounter getCounter(String node, String tenant) {
    return nodeTenantCounters.get(new NodeTenantKey(node, tenant));
  }
}
