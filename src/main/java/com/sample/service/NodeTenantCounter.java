package com.sample.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.sample.resources.CacheOps;

public class NodeTenantCounter {

  private NodeTenantKey key;
  private CacheOps cacheOps;
  private AtomicInteger counter = new AtomicInteger(0);
  private final NodeTenantCounterUpdateBatchTask[] counterUpdateBatchTask =
      new NodeTenantCounterUpdateBatchTask[1];

  public static final int MAX_BATCH_OPEN_MS = 250;
  private static ExecutorService executor = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 60L,
      TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

  public NodeTenantCounter(String node, String tenant, CacheOps cacheOps) {
    this.key = new NodeTenantKey(node, tenant);
    this.cacheOps = cacheOps;
  }

  public NodeTenantKey getKey() {
    return key;
  }

  public String getNode() {
    return key.getNode();
  }

  public String getTenant() {
    return key.getTenant();
  }

  public AtomicInteger getCounter() {
    return counter;
  }

  public void setKey(NodeTenantKey key) {
    this.key = key;
  }

  public void increment() {
    TestLog.log("increment counter");
    counter.getAndIncrement();
    batchChangeRequest();
  }

  public void decrement() {
    TestLog.log("decrement counter");
    counter.getAndDecrement();
    batchChangeRequest();
  }

  private void batchChangeRequest() {
    boolean batchSucceed = false;
    try {
      if (counterUpdateBatchTask[0] != null && counterUpdateBatchTask[0].addRequest(this.key)) {
        batchSucceed = true;
      }
    } catch (Exception ex) {
      // can ignore any exception thrown here??
    }

    if (!batchSucceed) {
      // new request to batch or MAX_BATCH_OPEN_MS elapses
      synchronized (this) {
        if (counterUpdateBatchTask[0] == null || !counterUpdateBatchTask[0].addRequest(this.key)) {
          NodeTenantCounterUpdateBatchTask updateBatchTask =
              new NodeTenantCounterUpdateBatchTask(this, cacheOps);
          counterUpdateBatchTask[0] = updateBatchTask;
          updateBatchTask.addRequest(key);
          executor.execute(updateBatchTask);
        }
      }
    }
  }
}
