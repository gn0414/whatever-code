package cn.simon.future;

import java.util.concurrent.*;

/**
 * @author simon
 *
 * 谈论到FutureTask 案例一
 * 我们清楚了future接口是为异步任务而生
 * 那么自然实现了future + runnable 接口的 runablefuture 就是我们异步线程的最基础接口了
 * 那么我们清楚 我们异步任务是可能被终止、打断（即执行异常或者中断异常）
 * 并且是拥有返回值，那么future接口的设计逻辑也就不难理解了
 *
 * 本案例侧重于使用异步任务配合线程池实现减小任务时间开销
 */
public class FutureTaskDemo1 {
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        onlyMain();
        futureWork();
    }




    public static void onlyMain(){
        long start = System.currentTimeMillis();
        try {Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        try {Thread.sleep(300);} catch (InterruptedException e) {throw new RuntimeException(e);}
        try {Thread.sleep(300);} catch (InterruptedException e) {throw new RuntimeException(e);}
        long end = System.currentTimeMillis();
        System.out.println("onlyMain cost:" +(end - start) +"ms");
    }

    public static void futureWork() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        FutureTask<String> futureTask1 = new FutureTask<>(()->{
            try {Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
            return "task1 over";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<>(()->{
            try {Thread.sleep(300);} catch (InterruptedException e) {throw new RuntimeException(e);}
            return "task2 over";
        });
        threadPool.submit(futureTask2);
        //主线程执行自己的逻辑
        try {Thread.sleep(500);} catch (InterruptedException e) {throw new RuntimeException(e);}
        //执行完获取结果
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        long end = System.currentTimeMillis();
        System.out.println("futureWork cost:" +(end - start) +"ms");

        //释放资源
        threadPool.shutdown();
    }

}
