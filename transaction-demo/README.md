

1、数据库
mysql 8.x

建库脚本
```sql
CREATE DATABASE `tansaction` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_as_cs';
```

2、启动配置

配置jdbc后启动遇到
```sql

Caused by: java.lang.RuntimeException: Driver com.mysql.cj.jdbc.Driver claims to not accept jdbcUrl, jdbc:mysql//localhost:3306/transaction
	at com.zaxxer.hikari.util.DriverDataSource.<init>(DriverDataSource.java:109) ~[HikariCP-5.1.0.jar:na]
	at com.zaxxer.hikari.pool.PoolBase.initializeDataSource(PoolBase.java:327) ~[HikariCP-5.1.0.jar:na]
```
发现是自己写错连接串信息了，应该是`jdbc:mysql://localhost:3306/transaction`


问题2 flyway提示  FlywayConfiguration.class]: Unsupported Database: MySQL 8.4

flyway的版本是 10.10.0 

因为flyway10.x版本后将数据库类型变成了插拔式的依赖，这里需要引入mysql
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency```