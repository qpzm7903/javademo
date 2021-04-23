使用netty做的httpserver，压测结果如下
```shell
PS C:\Windows\system32> sb -u http://localhost:8808/test -c 40 -N 30
Starting at 2021/4/20 7:43:10
[Press C to stop the test]
126920  (RPS: 3610.8)
---------------Finished!----------------
Finished at 2021/4/20 7:43:45 (took 00:00:35.2471558)
Status 200:    126943

RPS: 4083.2 (requests/second)
Max: 205ms
Min: 0ms
Avg: 0.5ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 3ms
  98%   below 8ms
  99%   below 12ms
99.9%   below 42ms
```