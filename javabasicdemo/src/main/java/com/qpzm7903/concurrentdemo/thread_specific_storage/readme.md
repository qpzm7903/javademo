给每个线程分配一个存储空间，ThreadLocal就是这么一个对象。对于所有线程来说，入口都是ThreadLocal，但是进去之后都是当前线程的空间。

使用ThreadLocal默认隐藏了上下文，上下文方便使用，但是难以理解，因为看不到这东西。

对于看不到的东西，总有些不敢相信。