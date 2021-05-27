
## 问题

### 使用jdbc、h2创建表后，在mybatis中查询却提示没了
https://stackoverflow.com/questions/5763747/h2-in-memory-database-table-not-found
原因：
> hbm2ddl closes the connection after creating the table, so h2 discards it.

也就是在某次连接关闭后，数据就都丢了，所以需要配置

`DB_CLOSE_DELAY=-1`

完整的url为

`jdbc:h2:mem:test;DB_CLOSE_DELAY=-1
`