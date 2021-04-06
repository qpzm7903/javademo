线程终止的时候不是直接停止，而是先进行一定的处理工作，再进行终止线程操作。

使用`java.concurrent.CountDownLatch`可以实现等待指定次数
