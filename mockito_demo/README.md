目的：研究springboot事件系统，发送事件和接收事件的关系，如何通过不同的事件类型分发的

最终在决定一个listner是否支持某个类型位置

org.springframework.context.event.GenericApplicationListenerAdapter.supportsEventType

通过判断listener的泛型参数 是否能够 isAssignableFrom 事件类型决定

例如listener类型时 Event<AA> ，Event是Event<?>那么这个类时没有继承关系的

那么为什么识别到的Event Type是  Event<?>呢？

其实在发送事件的事后，对象是声明类类型的，例如Event<AA>

但是在收到的时候还丢了


计划

- [x] 抽象的事件分发
- [x] 支持listener的同步、异步 
- [x] 支持多层实体继承关系分发1

TODO

- [ ] 跑UT时一直提示  Java HotSpot(TM) 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
