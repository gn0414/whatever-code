package cn.simon.lock;


/**
 * 循环双向等待死锁demo
 */
public class DeadLockDemo04 {

    public static void main(String[] args) {

        Object a = new Object();
        Object b = new Object();

        Thread t1 = new Thread(()->{
            synchronized (a){
                System.out.println("t1获取到锁a");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (b){
                    System.out.println("t1获取到锁b");
                }
            }
        });


        Thread t2 = new Thread(()->{
            synchronized (b){
                System.out.println("t2获取到锁b");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (a){
                    System.out.println("t2获取到锁a");
                }
            }
        });

        t1.start();
        t2.start();
    }
}
