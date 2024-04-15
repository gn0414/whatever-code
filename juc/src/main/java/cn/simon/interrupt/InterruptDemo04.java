package cn.simon.interrupt;

/**
 * @author Simon
 *
 * 实例线程未处于阻塞状态时,调用interrupt方法不会立刻停止线程证明
 */
public class InterruptDemo04 {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            for (int i = 1; i <= 300; i++) {
                System.out.println("------- "+i);
            }
        },"t1");
        System.out.println("t1的中断标志： "+t1.isInterrupted());
        t1.start();

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        t1.interrupt();
        System.out.println("t1调用interrupt之后的中断标志： "+t1.isInterrupted());

    }
}
