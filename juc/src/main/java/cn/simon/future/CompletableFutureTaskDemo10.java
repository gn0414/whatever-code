package cn.simon.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author simon
 * 我们的CompletableFutureTask 运行对线程池选择
 * 我们发现许多的方法里面有thenxxxAsync
 * 我的理解
 * 其实对于我个人的理解就是
 * jdk 8(以下内容)
 * 因为我们知道我们的CompletableFutureTask 是基于实现了CompletionStage这个接口
 * 那么很有可能我们的thenxxx这些方法均是下一个阶段的意思，同一个阶段使用相同的线程
 * 这就是为什么我们的demo1中都是一个worker的原因
 * demo2也非常可见的事就是均使用async可能就是不同的线程了（我们记得很清楚，我们的ComplteableFutureTask自带ForkJoin线程池）
 * 那么我们可以很明显看见 一二号使用的是worker1 三四号用的是worker2(可能可能！具体会优化,可能就是会选择运行最快的一种方式)
 * 自然我们可以猜测是demo3的线程使用情况了（所有阶段全部用的是同一个线程） worker1 亲测在 jdk17 会变成main 还不知道为什么
 * 自然async没指定线程池的话就会默认forkjoinpool
 */
public class CompletableFutureTaskDemo10 {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        //demo1
        System.out.println("demo1---------------");
        try {
            CompletableFuture.supplyAsync(()->{
                System.out.println("一号任务" + Thread.currentThread().getName());
                return "abcd";
            }).thenRun(
                    () ->{
                        System.out.println("二号任务" + Thread.currentThread().getName());
                    }
            ).thenRun(
                    () ->{
                        System.out.println("三号任务" + Thread.currentThread().getName());
                    }
            ).thenRun(
                    () ->{
                        System.out.println("四号任务" + Thread.currentThread().getName());
                    }
            ).join();

            //demo2
            System.out.println("demo2---------------");
            CompletableFuture.supplyAsync(()->{
                System.out.println("一号任务" + Thread.currentThread().getName());
                return "abcd";
            }).thenRunAsync(
                    () ->{
                        try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
                        System.out.println("二号任务" + Thread.currentThread().getName());
                    }
            ).thenRunAsync(
                    () ->{
                        try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
                        System.out.println("三号任务" + Thread.currentThread().getName());
                    }
            ).thenRunAsync(
                    () ->{
                        try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}
                        System.out.println("四号任务" + Thread.currentThread().getName());
                    }
            ).join();

            System.out.println("demo3---------------");

            //demo3
            CompletableFuture.supplyAsync(()->{
                System.out.println("一号任务" + Thread.currentThread().getName());
                return "abcd";
            },threadPool).thenRun(
                    () ->{
                        try {Thread.sleep(20);} catch (InterruptedException e) {throw new RuntimeException(e);}
                        System.out.println("二号任务" + Thread.currentThread().getName());
                    }
            ).thenRun(
                    () ->{
                        try {Thread.sleep(20);} catch (InterruptedException e) {throw new RuntimeException(e);}
                        System.out.println("三号任务" + Thread.currentThread().getName());
                    }
            ).thenRun(
                    () ->{
                        try {Thread.sleep(20);} catch (InterruptedException e) {throw new RuntimeException(e);}
                        System.out.println("四号任务" + Thread.currentThread().getName());
                    }
            );


            //demo4
            System.out.println("demo4---------------");
            CompletableFuture.supplyAsync(()->{
                System.out.println("一号任务" + Thread.currentThread().getName());
                return "abcd";
            },threadPool).thenRunAsync(
                    () ->{
                        try {Thread.sleep(20);} catch (InterruptedException e) {throw new RuntimeException(e);}
                        System.out.println("二号任务" + Thread.currentThread().getName());
                    }
            ).thenRunAsync(
                    () ->{
                        try {Thread.sleep(20);} catch (InterruptedException e) {throw new RuntimeException(e);}
                        System.out.println("三号任务" + Thread.currentThread().getName());
                    }
            ).thenRunAsync(
                    () ->{
                        try {Thread.sleep(20);} catch (InterruptedException e) {throw new RuntimeException(e);}
                        System.out.println("四号任务" + Thread.currentThread().getName());
                    }
            ).join();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}