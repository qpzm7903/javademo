定义
> Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.

> 将一组对象组织成树形结构，以表示一种‘部分 - 整体’的层次结构。组合模式让客户端可以统一单个对象和组合对象的处理逻辑（递归遍历）


样例：
1、雇佣者

2、文件系统

3、双写

双写一般有两个repo，client需要连续调用了两次，但是组合模式可以让client使用一个对象那样使用一组对象。

