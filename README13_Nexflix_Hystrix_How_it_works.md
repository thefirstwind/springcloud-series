# Nexfilx Hystrix How it Works

<!--https://github.com/Netflix/Hystrix/wiki/How-it-Works-->

##  Contents
* 1 [Flow Chart](#FlowChart)<!-- @IGNORE PREVIOUS: anchor -->
* 2 [Sequence Diagram](#SequenceDiagram)<!-- @IGNORE PREVIOUS: anchor -->
* 3 [Circuit Breaker](#CircuitBreaker)<!-- @IGNORE PREVIOUS: anchor -->
* 4 [Isolation](#Isolation)<!-- @IGNORE PREVIOUS: anchor -->
* 5 [Threads & Thread Pools](#Threads_Thread_Pools)<!-- @IGNORE PREVIOUS: anchor -->
* 6 [Request Collapsing](#Request_Collapsing)<!-- @IGNORE PREVIOUS: anchor -->
* 7 [Request Caching](#Request_Caching)<!-- @IGNORE PREVIOUS: anchor -->


## 1 Flow Chart <a name="FlowChart"><a>
The following diagram shows what happens when you make a request to a service dependency by means of Hystrix:

![](_images/hystrix-command-flow-chart.png)

The following sections will explain this flow in greater detail:

* [Construct a HystrixCommand or HystrixObservableCommand Object](#anchor_1_1)<!-- @IGNORE PREVIOUS: anchor -->
* [Execute the Command](#anchor_1_2)<!-- @IGNORE PREVIOUS: anchor -->
* [Is the Response Cached?](#anchor_1_3)<!-- @IGNORE PREVIOUS: anchor -->
* [Is the Circuit Open?](#anchor_1_4)<!-- @IGNORE PREVIOUS: anchor -->
* [Is the Thread Pool/Queue/Semaphore Full?](#anchor_1_5)<!-- @IGNORE PREVIOUS: anchor -->
* [HystrixObservableCommand.construct() or HystrixCommand.run()](#anchor_1_6)<!-- @IGNORE PREVIOUS: anchor -->
* [Calculate Circuit Health](#anchor_1_7)<!-- @IGNORE PREVIOUS: anchor -->
* [Get the Fallback](#anchor_1_8)<!-- @IGNORE PREVIOUS: anchor -->
* [Return the Successful Response](#anchor_1_9)<!-- @IGNORE PREVIOUS: anchor -->


### 1.1 Construct a HystrixCommand or HystrixObservableCommand Object <a name="anchor_1_1"><a>

### 1.2 Execute the Command <a name="anchor_1_2"><a>

### 1.3 Is the Response Cached? <a name="anchor_1_3"><a>

### 1.4 Is the Circuit Open? <a name="anchor_1_4"><a>

### 1.5 Is the Thread Pool/Queue/Semaphore Full? <a name="anchor_1_5"><a>

### 1.6 HystrixObservableCommand.construct() or HystrixCommand.run() <a name="anchor_1_6"><a>

### 1.7 Calculate Circuit Health <a name="anchor_1_7"><a>

### 1.8 Get the Fallback <a name="anchor_1_8"><a>

### 1.9 Return the Successful Response <a name="anchor_1_9"><a>
```









```

## 2 Sequence Diagram <a name="SequenceDiagram"><a>
```









```
## 3 Circuit Breaker <a name="CircuitBreaker"><a>

```









```
## 4 Isolation <a name="Isolation"><a>

```









```
## 5 Threads & Thread Pools <a name="Threads_Thread_Pools"><a>
```









```
## 6 Request Collapsing <a name="Request_Collapsing"><a>
```









```
## 7 Request Caching <a name="Request_Caching"><a>
```









```

