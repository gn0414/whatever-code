package cn.simon.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁实现demo 复习CAS
 */
public class CASDemo04 {
    //我们知道我们自旋锁的基础就是我们的CAS（原子操作），那么思路我们自然就是有了
    //判断当前线程坑是否为null，如果是则锁为空
    //判断线程坑的线程是否为自己，为自己则解锁

    AtomicReference<Thread> lock = new AtomicReference<>();

    public  void lock(){

        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+" come in");
        while (!lock.compareAndSet(null, thread)) {
            System.out.println(Thread.currentThread().getName()+ " is in while");
        }
    }

    public void unLock(){
        Thread thread = Thread.currentThread();
        lock.compareAndSet(thread,null);
        System.out.println(thread.getName()+" task over unlock");
    }


    public static void main(String[] args) {
        CASDemo04 casDemo04 = new CASDemo04();

        Thread t1 = new Thread(() -> {
            casDemo04.lock();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            casDemo04.unLock();
        },"t1");
        t1.start();

        Thread t2 = new Thread(()->{
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            casDemo04.lock();
            casDemo04.unLock();
        },"t2");

        t2.start();
    }
}
