package com.aibany.jpool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class AJFactory extends BasePooledObjectFactory<AJTaskObject>{

    @Override
    public AJTaskObject create() throws Exception {
        AJTaskObject task = new AJTaskObject();
        return task;
    }

    @Override
    public PooledObject<AJTaskObject> wrap(AJTaskObject obj) {

        return new DefaultPooledObject<>(obj);
    }

    public static GenericObjectPool<AJTaskObject> createDefaultPool() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setLifo(false);
        config.setMaxTotal(5);
        config.setMaxWaitMillis(-1);
        config.setMinIdle(0);
        return new GenericObjectPool<AJTaskObject>(new AJFactory(),config);
    }
}
