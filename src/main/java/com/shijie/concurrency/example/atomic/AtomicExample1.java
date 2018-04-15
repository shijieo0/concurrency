package com.shijie.concurrency.example.atomic;

import com.shijie.concurrency.annoations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示 AtomicXXX
 * Created by ShiJie on 2018-04-15 15:08
 */
@ThreadSafe
public class AtomicExample1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtomicExample1.class);

    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 50;

    public static AtomicInteger count = new AtomicInteger(0);

    private static void add() {
        count.incrementAndGet(); // 先增加再获取
//        count.getAndIncrement(); // 先获取再增加，要取返回值时有区别，若只做+1操作，无影响
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    LOGGER.error("exception: {}", e);
                }
                countDownLatch.countDown(); // 每执行完一次，计数减1
            });
        }
        countDownLatch.await(); // 保证所有线程执行完，计数为0
        executorService.shutdown();
        LOGGER.info("Count: {}", count.get());
    }

}
