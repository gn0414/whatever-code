package cn.simon.locksupport;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitAndSignalDemo02 {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
//        m1(lock);
//        error1(lock);
        error2(lock);
    }

    public static void m1(ReentrantLock lock){

        Condition condition = lock.newCondition();

        Thread t1 = new Thread(
                ()->{
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + "come in");
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(Thread.currentThread().getName() + "被唤醒");
                    }finally {
                        lock.unlock();
                    }
                },"t1"
        );
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread t2 = new Thread(
                ()->{
                    lock.lock();
                    try {
                        condition.signal();
                        System.out.println(Thread.currentThread().getName() + "发出通知");
                    }finally {
                        lock.unlock();
                    }
                },"t2"
        );

        t2.start();
    }

    /**
     * await 和 signal只能在lock锁块里面进行使用
     * @param lock 锁对象
     */

    public static void error1(ReentrantLock lock){

        Condition condition = lock.newCondition();

        Thread t1 = new Thread(
                ()->{
//                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + "come in");
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(Thread.currentThread().getName() + "被唤醒");
                    }finally {
//                        lock.unlock();
                    }
                },"t1"
        );
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread t2 = new Thread(
                ()->{
//                    lock.lock();
                    try {
                        condition.signal();
                        System.out.println(Thread.currentThread().getName() + "发出通知");
                    }finally {
//                        lock.unlock();
                    }
                },"t2"
        );

        t2.start();
    }

    /**
     * await和signal注意使用顺序,一定要先await再signal,否则无法唤醒
     * @param lock 锁对象
     *
     */

    public static void error2(ReentrantLock lock){

        Condition condition = lock.newCondition();

        Thread t1 = new Thread(
                ()->{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + "come in");
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(Thread.currentThread().getName() + "被唤醒");
                    }finally {
                        lock.unlock();
                    }
                },"t1"
        );
        t1.start();



        Thread t2 = new Thread(
                ()->{
                    lock.lock();
                    try {
                        condition.signal();
                        System.out.println(Thread.currentThread().getName() + "发出通知");
                    }finally {
                        lock.unlock();
                    }
                },"t2"
        );

        t2.start();
    }
}
