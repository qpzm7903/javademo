首先编译注解处理器，然后编译被注解的类

powershell下
```shell
mkdir out
javac -cp "$env:JAVA_HOME/lib/tools.jar" src/com/qpzm/anno/*  -d ./out
javac -cp ./out  -d out -processor com.qpzm.anno.DataAnnotationProcessor src/com/qpzm/test/User.java
java -cp ./out com.qpzm.test.User
```
因为tools不再jre里面，所以得指定

git bash 下
```shell
mkdir out
javac -cp "$JAVA_HOME/lib/tools.jar" src/com/qpzm/anno/*  -d ./out
javac -cp ./out  -d out -processor com.qpzm.anno.DataAnnotationProcessor src/com/qpzm/test/User.java
java -cp ./out com.qpzm.test.User
```
