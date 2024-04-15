package cn.simon.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo03 {

    //LockSupport通过一种名为Permit（许可证）的概念来做到唤醒和阻塞线程的，每个线程均有一个许可（permit）
    //但与Semaphore不同的是，许可的累加上限为1
    //这个将等待和唤醒顺序的问题解决了，即可以先唤醒 直到调用等待立刻唤醒
    //同样也解决了一定要在锁块里面的要求
    public static void main(String[] args) {
        Thread t1 = new Thread(
                ()->{
                    System.out.println(Thread.currentThread().getName() + "come in");
                    LockSupport.park();
                    LockSupport.park();
                    System.out.println(Thread.currentThread().getName() + "被唤醒");

                },"t1"
        );
        t1.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread t2 = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "发出通知");
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
        },"t2");
        t2.start();
    }
}
