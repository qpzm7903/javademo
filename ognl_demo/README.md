# 参考
官网

- https://commons.apache.org/proper/commons-ognl/language-guide.html

博客
- https://juejin.cn/post/6844904013859651597
# 错误

## MemberAccess implementation must be provided

这个错误提示是因为 Ognl 默认情况下是禁止访问 Java 对象的私有成员的，如果你在 Ognl 表达式中使用了私有成员（比如私有方法、私有属性等），就会触发这个错误。

要解决这个问题，你需要自定义一个 MemberAccess 实现，用于控制 Ognl 是否允许访问 Java 对象的私有成员。你可以继承 DefaultMemberAccess 类并重写其 isAccessible 方法，根据需要来决定是否允许访问私有成员。

```java
import ognl.DefaultMemberAccess;
import java.lang.reflect.AccessibleObject;

public class MyMemberAccess extends DefaultMemberAccess {

    public boolean isAccessible(Object target, AccessibleObject member, String propertyName) {
        // 根据需要来决定是否允许访问私有成员
        return true; // 这里示例都返回 true，即允许访问私有成员
    }
}


```


```java
import ognl.Ognl;
import ognl.OgnlContext;

OgnlContext context = new OgnlContext();
context.setMemberAccess(new MyMemberAccess());

// 在 context 中执行 Ognl 表达式
Object result = Ognl.getValue(expression, context, root);

```