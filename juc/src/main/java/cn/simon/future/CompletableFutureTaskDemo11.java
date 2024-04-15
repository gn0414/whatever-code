package cn.simon.future;


import java.util.concurrent.CompletableFuture;

/**
 * @author simon
 *
 * 那么我可以选择执行方式呢（也就是谁快用谁）
 * 没错其实都会执行
 */
public class CompletableFutureTaskDemo11 {

    //对计算速度进行选用
    public static void main(String[] args) {
        CompletableFuture<String> playerA = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("a come in");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "playerA";
                }
        );

        CompletableFuture<String> playerB = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("b come in");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "playerB";
                }
        );
        System.out.println(playerA.applyToEither(playerB, f -> {
            return f + " is winner";
        }).join());
    }
}
