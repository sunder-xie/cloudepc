package com.tqmall.data.epc.biz.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by huangzhangting on 16/8/15.
 */
@Deprecated
@Service
@Slf4j
public class ThreadBizImpl implements ThreadBiz{

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(3, new ThreadFactory() {

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    @Override
    public void execute(Runnable task) {
        EXECUTOR_SERVICE.execute(task);
    }

    @PreDestroy
    public void destroy() {
        EXECUTOR_SERVICE.shutdown();
        log.info("========== ThreadBiz successfully shutdown ==========");
    }

}
