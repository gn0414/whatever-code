package cn.simon.future;


import java.util.concurrent.CompletableFuture;

/**
 * @author simon
 *
 * 对计算结果进行合并
 * 其他任务完成就等待其他分支任务
 */
public class CompletableFutureTaskDemo12 {

    public static void main(String[] args) {
        //先完成先等着等着其他任务

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println(Thread.currentThread().getName() + "----启动");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "----完成");
                    return 10;
                }
        );

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println(Thread.currentThread().getName() + "----启动");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "----完成");
                    return 20;
                }
        );

        CompletableFuture<Integer> future = future1.thenCombine(future2, (x, y) -> {
            System.out.println("结果合并");
            return x + y;
        });

        System.out.println("结果为: "+future.join());
    }
}
