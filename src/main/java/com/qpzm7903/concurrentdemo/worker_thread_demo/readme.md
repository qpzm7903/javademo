也称之为线程池模式

在channel中维护一个workThread的数组，就是一个线程池。

java的并发包里面有相关的线程池类。

比如 


- java.util.concurrent.ThreadPoolExecutors
- java.util.concurrent.Executors

    - newFixedThreadPool
    - newCachedThreadPool
    - newScheduledTheadPool
    
