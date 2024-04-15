package cn.simon.future;

import java.util.concurrent.CompletableFuture;

/**
 * @author simon
 * 对计算结果进行消费
 */
public class CompletableFutureTaskDemo9 {
    public static void main(String[] args) {

        //thenAccept 消费 (上一个阶段的值我需要用并且不会传给下一个阶段)
        //thenRun 无相关执行(上一个阶段的值我不需要用并且不会传给下一个阶段)
        //thenApply 转化 (上一个阶段的值我需要用并且会传给下一个阶段)
        CompletableFuture.supplyAsync(
                ()-> 1
        ).thenApply(
                f -> f+1
        ).thenApply(
                f -> f+2
        ).thenAccept(
                f -> System.out.println(f)
        ).thenRun(
                () -> System.out.println("then run")
        );



    }
}
