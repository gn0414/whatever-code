package cn.simon.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author simon
 * 计算结果进行处理
 */
public class CompletableFutureTaskDemo8 {
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    public static void main(String[] args) {
        //1.对计算结果进行处理
        //thenApply （无法接受异常,若有异常马上叫停）
        CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("111");
                    return 1;
                },threadPool)
                .thenApply(
                        f ->{
                            System.out.println("222");
                            return f+2;
                        }
                ).thenApply(
                        f ->{
                            System.out.println("333");
                            return f+3;
                        }
                ).whenComplete((v,e) ->{
                    if (e == null){
                        System.out.println("---计算结果 "+v);
                    }
                }).exceptionally(e -> {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                    return null;
                });

        System.out.println("主线程去干活");

        //2.handle 有异常也可以继续，根据异常返回参数继续
        CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("111");
                    return 1;
                },threadPool)
                .handle((f,e) ->{
                    System.out.println("222");
                    System.out.println(10/0);
                    return f+2;
                }).exceptionally(
                e ->{
                    System.out.println(e.getMessage());
                    return 0;
                })
                .handle((f,e) -> {
                    System.out.println("333");
                    return f+3;
                }).whenComplete((v,e) ->{
                    if (e == null){
                        System.out.println("---计算结果 "+v);
                    }
                });

        threadPool.shutdown();
    }


}
