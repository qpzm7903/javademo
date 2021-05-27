## 单例模式
1. 构造函数是private的，不对外暴露
2. 对象创建的时候要线程安全
3. 是否考虑延迟加载
4. 获取单例性能是否高
### 饿汉式
启动的时候就将类初始化
```java

public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private static final IdGenerator instance = new IdGenerator();
  private IdGenerator() {}
  public static IdGenerator getInstance() {
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

### 懒汉模式
使用的时候才加载，并且用锁锁住，有性能问题。只能应付偶尔使用的场景
```java
public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private static final IdGenerator instance;
  private IdGenerator() {}
  public synchronized static IdGenerator getInstance() {
      if (instance == null){
          instance = new IdGenerator();
          return instance;
      }
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

### 双重检测

```java
public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private static final IdGenerator instance;
  private IdGenerator() {}
  public  static IdGenerator getInstance() {
      if (instance == null){
        synchronized(IdGenerator.class){
        if (instance == null){
          instance = new IdGenerator();
        }
        return instance;
        }
      }
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

### 静态内部类
交给 JVM 处理
```java

public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private IdGenerator() {}

  private static class SingletonHolder{
    private static final IdGenerator instance = new IdGenerator();
  }
  
  public static IdGenerator getInstance() {
    return SingletonHolder.instance;
  }
 
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

### 枚举
```java

public enum IdGenerator {
  INSTANCE;
  private AtomicLong id = new AtomicLong(0);
 
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

### 缺点

### 解决

### 进程内单例 和 线程内单例
进程内单例也自然是线程内单例。

一个进程可以有多个线程，线程单例就是进程中的每个线程都可以有自己的单例对象，不同线程之间不同。ThreadLocal就是java提供的线程单例。