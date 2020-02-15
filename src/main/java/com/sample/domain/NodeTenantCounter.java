package com.sample.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class NodeTenantCounter {
  
  private NodeTenantKey key;
  private AtomicInteger counter;
    
  public NodeTenantKey getKey() {
    return key;
  }
  
  public String getNode() {
    return key.getNode();
  }

  public String getTenant() {
    return key.getTenant();
  }
  
  public AtomicInteger  getCounter() {
    return counter;
  }
  
  public void setKey(NodeTenantKey key) {
    this.key = key;
  }

  public void increment() {
    // counter.accumulateAndGet(x, accumulatorFunction)
    // counter.incrementAndGet();
    counter.getAndIncrement();
  }
  
  public void decrement() {
    // counter.decrementAndGet();
    counter.getAndDecrement();
  }
  
  private void notifyCachePut() {
            
  }  
  

}
