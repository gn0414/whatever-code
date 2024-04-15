package cn.simon.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author simon
 *
 * 下面的案例表示
 * 我们的futurtask还是有一些缺点的
 * 简单的场景确实足够应付
 * 我们的get方法获取结果是会阻塞直到异步任务完成
 * 我们的isDone方法轮询也会发生空转
 * 所以我们希望能有更加好的解决方案
 *
 * 例如:
 * 1.结果回调
 * 2.便捷创建异步任务线程
 * 3.任务依赖执行
 * 4.计算速度
 */
public class FutureTaskDemo2 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(()->{
            System.out.println("异步线程开始执行任务");
            Thread.sleep(5000);
            return "task over";
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println("主线程执行...");
        //        System.out.println(futureTask.get()); 阻塞
        int count = 1;
        while (true){
            if (futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }
            Thread.sleep(500);
            System.out.println("轮询次数:"+count++);
        }

    }
}
