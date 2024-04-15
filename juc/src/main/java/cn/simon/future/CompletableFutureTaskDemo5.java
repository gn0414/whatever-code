package cn.simon.future;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;

/**
 * @author simon
 * 只是谈论下 get 和 join的区别
 * join 会将实际的异常给处理为异步任务异常，也就是不需要我们手动抛出（推测可能是他自己catch了，然后抛出自定义的异步任务异常）
 * get 会将实际的异常抛出（功能都是一样的）
 */
public class CompletableFutureTaskDemo5 {
    public static void main(String[] args) {
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return "hello 1234";
        });

//        System.out.println(stringCompletableFuture.get());

        System.out.println(stringCompletableFuture.join());

    }
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
class Student{
    private Integer id;

    private String name;

    private String major;
}
