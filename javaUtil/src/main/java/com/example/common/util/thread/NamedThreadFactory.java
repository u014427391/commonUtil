package com.example.common.util.thread;

import org.springframework.util.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    /** 线程组 **/
    private final ThreadGroup group;
    /** 原子类保证计数线程安全 **/
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    /** 命名前缀 **/
    private final String namePrefix;

    NamedThreadFactory(String threadFactoryName) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        threadFactoryName = StringUtils.isEmpty(threadFactoryName)
                ? "pool-"
                : threadFactoryName + "-";
        namePrefix = threadFactoryName + poolNumber.getAndIncrement()+ "-thread-";
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        // 守护线程
        if (t.isDaemon())
            t.setDaemon(false);
        // 优先级
        if (t.getPriority() != Thread.NORM_PRIORITY)
            // 标准优先级
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}

