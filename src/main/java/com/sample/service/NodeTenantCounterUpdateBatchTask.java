package com.sample.service;

import java.util.concurrent.TimeUnit;

import com.sample.resources.CacheOps;

public class NodeTenantCounterUpdateBatchTask implements Runnable {

  private boolean closed = false;
  private boolean modified = false;
  private NodeTenantCounter counter;
  private CacheOps cacheOps;

  public NodeTenantCounterUpdateBatchTask(NodeTenantCounter counter, CacheOps cacheOps) {
    this.counter = counter;
    this.cacheOps = cacheOps;
  }

  public boolean addRequest(NodeTenantKey key) {
    if (closed) {
      return false;
    }

    modified = true;
    return true;
  }

  @Override
  public void run() {
    try {
      long deadlineMs = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS)
          + NodeTenantCounter.MAX_BATCH_OPEN_MS + 1;
      long t = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);

      synchronized (this) { // not sure why we need lock
        while (!closed && (t < deadlineMs)) {
          t = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);

          // zero means "wait forever", can't have that.
          long toWait = Math.max(1, deadlineMs - t);
          wait(toWait);
        }

        closed = true;

        // update cache
        if (modified) {
          putCache();
        }
      }
    } catch (Exception ex) {
      // add proper error handling
    }
  }

  private void putCache() {
    this.cacheOps.putCache(counter.getKey().asCacheKey(), counter.getCounter().intValue());
  }

}
