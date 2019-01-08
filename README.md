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

```
package test;

import com.aibany.jpool.*;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoolTest {

    private static GenericObjectPool<AJTaskObject> pool;

    @Test
    public void testPool() throws Exception{

        //do some config
        pool = AJFactory.createDefaultPool();
        pool.setMaxIdle(2);

        //run tasks
        for (int i = 0; i < 100; i++) {
            callMethod(i);
        }

        //keep the loop if no server
        Thread.sleep(1000000);
    }

    private void callMethod(int index) {

        try {

            final Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("MyName", "Jack" + index);

            AJTaskObject taskObject = pool.borrowObject();
            taskObject.setUserObject(userInfo);

            taskObject.setDelegate(new AJTaskDelegate() {

                @Override
                public void taskShouldStart(AJTaskObject task) {
                    Map<String, Object> runInfo = (Map<String, Object>) task.getUserObject();
                    String myName = userInfo.get("MyName").toString();

                    System.out.println("I am " + myName + "， taskShouldStart, thread:" + Thread.currentThread().getName());
                }

                @Override
                public void taskFinished(boolean success, AJTaskObject task) {

                    Map<String, Object> runInfo = (Map<String, Object>) task.getUserObject();
                    String myName = userInfo.get("MyName").toString();

                    System.out.println("I am " + myName + "， taskFinished, thread:" + Thread.currentThread().getName());
                    pool.returnObject(task);
                }
            });
            new Thread(taskObject).start();

        } catch (Exception e) {
            System.out.println("Exception:" + e.toString());
        }
    }
}


```
