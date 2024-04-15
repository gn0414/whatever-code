package cn.simon.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author simon
 *
 * 会创建了,那么如何使用呢
 * 以及我们所提到的能够回调结果的函数呢
 * 没关系,下面就是我们实际演示的过程了
 * 并且还有异常处理
 */
public class CompletableFutureTaskDemo4 {
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try {
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(
                    () -> {
                        System.out.println(Thread.currentThread().getName() + "come in");
                        Random random = new Random();
                        int result;
                        try {
                            result = random.nextInt(10);
                            Thread.sleep(1000);
                            System.out.println("执行一秒后出结果:" + result);

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return result;
                    },threadPool).whenComplete((r,t) ->{
                //这个其实就是我们的回调函数了
                //完成了我们就打印结果
                System.out.println("任务完成,结果为:"+r);
            }).exceptionally(t -> {
                System.out.println("任务发生了异常");
                return null;
            });
            System.out.println(Thread.currentThread().getName()+"去忙其他的");
        }finally {
            threadPool.shutdown();
        }

        //但是我们发现为什么任务没有完成,就直接结束了程序呢
        //原来是因为默认使用的ForkJoinPool创建的线程是守护线程
        //所以我们使用自定义的线程池即可
    }
}
