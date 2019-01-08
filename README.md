# jpool
基于代理模式， 构建java 线程池， 用于诸如大批量消息发送、耗时任务执行等场景

A java Thread Pool Tool, designed as delegate-call. 
Can used for batch Instruction excute or Async excute.


因为生成maven repository比较麻烦(snapshot or release)，所以有需要的，请直接导人源码使用。

依赖
```
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
            <version>2.4.2</version>
        </dependency>

```

使用方法：
请参照：PoolTest.java
