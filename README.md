# springboot-cluster-connectionpool

I mimic the AmazonSQSBufferedAsyncClient implementation to build a high throughput connection pool counter cache put. The idea is to have buffer mechanism (200ms) to avoid update cache for every counter change.


