package cn.simon.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author simon
 *  我们在案例6进行了一个小案例
 *  那么要真正学会completetaskfuture
 *  这一讲就要对api进行学习
 *  1.获取结果和触发计算
 */
public class CompletableFutureTaskDemo7 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //1.获取结果和触发计算 get/join/getNow/complete
        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "abc";
                }
        );

//        System.out.println(future.get());
//        System.out.println(future.get(500,TimeUnit.MILLISECONDS));
//        System.out.println(future.join());

        //主要介绍下面两个
        //假设计算完了就返回计算值，若没有跑完则返回getNowTimeUnit.SECONDS.sleep(1);

//        TimeUnit.SECONDS.sleep(2);
//        System.out.println(future.getNow("getNow"));
        //complete 是否打断get方法立即返回括号值
        System.out.println(future.complete("complete") + "\t" + future.join());

    }
}
