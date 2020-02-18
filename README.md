# springboot-cluster-connectionpool

I mimic the AmazonSQSBufferedAsyncClient implementation to build a high throughput connection pool counter cache put. The idea is to have buffer mechanism (e.g. 200ms) to avoid update cache for every counter change. Connection pool counter synchronization across nodes can be done by jgroup or memcached, the project doesn't implement that as a whole.


