# Configuration

<!--https://github.com/Netflix/Hystrix/wiki/Configuration-->

##  Contents
* 1 [Introduction](#anchor100)<!-- @IGNORE PREVIOUS: anchor -->
* 2 [Command Properties](#anchor200)<!-- @IGNORE PREVIOUS: anchor -->
  * 2.1 [Execution](#anchor210)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.1.1 [execution.isolation.strategy](#anchor211)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.1.2 [execution.isolation.thread.timeoutInMilliseconds](#anchor212)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.1.3 [execution.timeout.enabled](#anchor213)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.1.4 [execution.isolation.thread.interruptOnTimeout](#anchor214)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.1.5 [execution.isolation.thread.interruptOnCancel](#anchor215)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.1.6 [execution.isolation.semaphore.maxConcurrentRequests](#anchor216)<!-- @IGNORE PREVIOUS: anchor -->
  * 2.2 [Fallback](#anchor220)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.2.1 [fallback.isolation.semaphore.maxConcurrentRequests](#anchor221)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.2.2 [fallback.enabled](#anchor222)<!-- @IGNORE PREVIOUS: anchor -->
  * 2.3 [Circuit Breaker(#anchor230)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.3.1 [circuitBreaker.enabled](#anchor231)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.3.2 [circuitBreaker.requestVolumeThreshold](#anchor232)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.3.3 [circuitBreaker.sleepWindowInMilliseconds](#anchor233)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.3.4 [circuitBreaker.errorThresholdPercentage](#anchor234)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.3.5 [circuitBreaker.forceOpen](#anchor235)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.3.6 [circuitBreaker.forceClosed](#anchor236)<!-- @IGNORE PREVIOUS: anchor -->
  * 2.4 [Metrics](#anchor240)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.4.1 [metrics.rollingStats.timeInMilliseconds](#anchor241)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.4.2 [metrics.rollingStats.numBuckets](#anchor242)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.4.3 [metrics.rollingPercentile.enabled](#anchor243)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.4.4 [metrics.rollingPercentile.timeInMilliseconds](#anchor244)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.4.5 [metrics.rollingPercentile.numBuckets](#anchor245)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.4.6 [metrics.rollingPercentile.bucketSize](#anchor246)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.4.7 [metrics.healthSnapshot.intervalInMilliseconds](#anchor247)<!-- @IGNORE PREVIOUS: anchor -->
  * 2.5 [Request Context](#anchor250)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.5.1 [requestCache.enabled](#anchor251)<!-- @IGNORE PREVIOUS: anchor -->
    * 2.5.2 [requestLog.enabled](#anchor252)<!-- @IGNORE PREVIOUS: anchor -->
* 3 [Collapser Properties](#anchor300)<!-- @IGNORE PREVIOUS: anchor -->
  * 3.1 [maxRequestsInBatch](#anchor310)<!-- @IGNORE PREVIOUS: anchor -->
  * 3.2 [timerDelayInMilliseconds](#anchor320)<!-- @IGNORE PREVIOUS: anchor -->
  * 3.3 [requestCache.enabled](#anchor330)<!-- @IGNORE PREVIOUS: anchor -->
* 4 [Thread Pool Properties](#anchor400)<!-- @IGNORE PREVIOUS: anchor -->
  * 4.1 [coreSize](#anchor410)<!-- @IGNORE PREVIOUS: anchor -->
  * 4.2 [maximumSize](#anchor420)<!-- @IGNORE PREVIOUS: anchor -->
  * 4.3 [maxQueueSize](#anchor430)<!-- @IGNORE PREVIOUS: anchor -->
  * 4.4 [queueSizeRejectionThreshold](#anchor440)<!-- @IGNORE PREVIOUS: anchor -->
  * 4.5 [keepAliveTimeMinutes](#anchor450)<!-- @IGNORE PREVIOUS: anchor -->
  * 4.6 [allowMaximumSizeToDivergeFromCoreSize](#anchor460)<!-- @IGNORE PREVIOUS: anchor -->
  * 4.7 [metrics.rollingStats.timeInMilliseconds](#anchor470)<!-- @IGNORE PREVIOUS: anchor -->
  * 4.8 [metrics.rollingStats.numBuckets](#anchor480)<!-- @IGNORE PREVIOUS: anchor -->






## 1 Introduction <a name="anchor100"><a>
## 2 Command Properties <a name="anchor200"><a>
### 2.1 Execution <a name="anchor210"><a>
#### 2.1.1 execution.isolation.strategy <a name="anchor211"><a>
#### 2.1.2 execution.isolation.thread.timeoutInMilliseconds <a name="anchor212"><a>
#### 2.1.3 execution.timeout.enabled <a name="anchor213"><a>
#### 2.1.4 execution.isolation.thread.interruptOnTimeout <a name="anchor214"><a>
#### 2.1.5 execution.isolation.thread.interruptOnCancel <a name="anchor215"><a>
#### 2.1.6 execution.isolation.semaphore.maxConcurrentRequests <a name="anchor216"><a>
### 2.2 Fallback <a name="anchor220"><a>
#### 2.2.1 fallback.isolation.semaphore.maxConcurrentRequests <a name="anchor221"><a>
#### 2.2.2 fallback.enabled <a name="anchor222"><a>
### 2.3 Circuit Breaker<a name="anchor230"><a>
#### 2.3.1 circuitBreaker.enabled <a name="anchor231"><a>
#### 2.3.2 circuitBreaker.requestVolumeThreshold <a name="anchor232"><a>
#### 2.3.3 circuitBreaker.sleepWindowInMilliseconds <a name="anchor233"><a>
#### 2.3.4 circuitBreaker.errorThresholdPercentage <a name="anchor234"><a>
#### 2.3.5 circuitBreaker.forceOpen <a name="anchor235"><a>
#### 2.3.6 circuitBreaker.forceClosed <a name="anchor236"><a>
### 2.4 Metrics <a name="anchor240"><a>
#### 2.4.1 metrics.rollingStats.timeInMilliseconds <a name="anchor241"><a>
#### 2.4.2 metrics.rollingStats.numBuckets <a name="anchor242"><a>
#### 2.4.3 metrics.rollingPercentile.enabled <a name="anchor243"><a>
#### 2.4.4 metrics.rollingPercentile.timeInMilliseconds <a name="anchor244"><a>
#### 2.4.5 metrics.rollingPercentile.numBuckets <a name="anchor245"><a>
#### 2.4.6 metrics.rollingPercentile.bucketSize <a name="anchor246"><a>
#### 2.4.7 metrics.healthSnapshot.intervalInMilliseconds <a name="anchor247"><a>
### 2.5 Request Context <a name="anchor250"><a>
#### 2.5.1 requestCache.enabled <a name="anchor251"><a>
#### 2.5.2 requestLog.enabled <a name="anchor252"><a>
## 3 Collapser Properties <a name="anchor300"><a>
### 3.1 maxRequestsInBatch <a name="anchor310"><a>
### 3.2 timerDelayInMilliseconds <a name="anchor320"><a>
### 3.3 requestCache.enabled <a name="anchor330"><a>
## 4 Thread Pool Properties <a name="anchor400"><a>
### 4.1 coreSize <a name="anchor410"><a>
### 4.2 maximumSize <a name="anchor420"><a>
### 4.3 maxQueueSize <a name="anchor430"><a>
### 4.4 queueSizeRejectionThreshold <a name="anchor440"><a>
### 4.5 keepAliveTimeMinutes <a name="anchor450"><a>
### 4.6 allowMaximumSizeToDivergeFromCoreSize <a name="anchor460"><a>
### 4.7 metrics.rollingStats.timeInMilliseconds <a name="anchor470"><a>
### 4.8 metrics.rollingStats.numBuckets <a name="anchor480"><a>
