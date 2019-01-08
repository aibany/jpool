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
