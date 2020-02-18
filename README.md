# springboot-cluster-connectionpool

I mimic the AmazonSQSBufferedAsyncClient implementation to build a high throughput connection pool counter cache put. The idea is to have buffer mechanism (e.g. 200ms) to avoid update cache for every counter change. Connection pool counter synchronization across nodes can be done by jgroup or memcached, the project doesn't implement that as a whole.

## bugs to fix
set MAX_BATCH_OPEN_MS to 30s and I can see two logs
[2020-02-18 01:04:58.412] waiting 30001ms on thread: pool-1-thread-2
[2020-02-18 01:05:28.413] waiting 1ms on thread: pool-1-thread-2


