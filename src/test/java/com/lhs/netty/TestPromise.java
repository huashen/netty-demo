package com.lhs.netty;

import io.netty.util.concurrent.*;

/**
 * TestPromise
 *
 * @author longhuashen
 * @since 2021-06-19
 */
public class TestPromise {

    public static void main(String[] args) {
        // 构造线程池
        EventExecutor executor = new DefaultEventExecutor();

        // 创建 DefaultPromise 实例
        Promise promise = new DefaultPromise(executor);

        // 下面给这个 promise 添加两个 listener
        promise.addListener(new GenericFutureListener<Future<Integer>>() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("任务结束，结果：" + future.get());
                } else {
                    System.out.println("任务失败，异常：" + future.cause());
                }
            }
        }).addListener(new GenericFutureListener<Future<Integer>>() {
            @Override
            public void operationComplete(Future future) throws Exception {
                System.out.println("任务结束，balabala...");
            }
        });

        // 提交任务到线程池，五秒后执行结束，设置执行 promise 的结果
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
                // 设置 promise 的结果
                 promise.setFailure(new RuntimeException());
//                promise.setSuccess(123456);
            }
        });

        // main 线程阻塞等待执行结果
        try {
            //阻塞等待任务结束，如果任务失败，将“导致失败的异常”重新抛出来
            promise.sync();

            // 阻塞等待任务结束，和 sync() 功能是一样的，不过如果任务失败，它不会抛出执行过程中的异常
            //promise.await();
        } catch (InterruptedException e) {
        }
    }
}
