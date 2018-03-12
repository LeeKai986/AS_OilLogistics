package com.zpf.oillogistics.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/9/11.
 * <p>
 * 单例线程池
 */

public enum SingletonPool {
    INSTANCE;

    private ExecutorService singlePool;

    private SingletonPool() {
        singlePool = Executors.newCachedThreadPool();
    }

    public ExecutorService getThreadPool() {
        return singlePool;
    }
}
