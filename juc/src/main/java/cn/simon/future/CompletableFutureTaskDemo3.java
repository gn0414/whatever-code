package cn.simon.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author simon
 * 什么是completablefuturetask
 * 就是java8之后对我上述提出问题的改进方案
 * 自然它仍然是异步的任务
 * 所以仍然实现了future接口
 * 并且通过实现compltablestage接口
 * 那么这个stage（阶段）是不是很符合我们所期望的任务依赖（异步计算的每一个阶段）
 * <p>
 * 我们自然就开始讲解CompletableFuture如何使用获得
 * 核心的四个静态方法
 */
public class CompletableFutureTaskDemo3 {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //1.无返回值 不依赖自定义线程池(我们通过输出可以看出来用的是ForkJoinPool线程池)
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(voidCompletableFuture.get());

        //2.无返回值 使用自定义线程池
        CompletableFuture<Void> voidPoolCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },threadPool);
        System.out.println(voidPoolCompletableFuture.get());


        //3.有返回值
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "stringCompletableFuture no pool";
        });
        System.out.println(stringCompletableFuture.get());

        //3.有返回值 自定义线程池
        CompletableFuture<String> stringPoolCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "stringCompletableFuture has pool";
        },threadPool);
        System.out.println(stringPoolCompletableFuture.get());

        threadPool.shutdown();
    }
}
