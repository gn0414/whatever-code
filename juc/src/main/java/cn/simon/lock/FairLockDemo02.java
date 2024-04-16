package cn.simon.lock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *公平锁和非公平锁演示code
 */
public class FairLockDemo02 {

    static int ticket = 50;
    public static void main(String[] args) {

        ReentrantLock noFairLock = new ReentrantLock();

        ReentrantLock fairLock = new ReentrantLock(true);

//       sellTicket(noFairLock);
        sellTicket(fairLock);

    }


    public static void sellTicket(Lock lock){
        Thread a = new Thread(()->{
            while (true) {
                lock.lock();
                try {
                    if (ticket > 0){
                        System.out.println(Thread.currentThread().getName() + " sell " + ticket--);
                    }else break;
                } finally {
                    lock.unlock();
                }
            }

        },"a");

        Thread b = new Thread(()->{
            while (true) {
                lock.lock();
                try {
                    if (ticket > 0){
                        System.out.println(Thread.currentThread().getName() + " sell " + ticket--);
                    }else break;
                } finally {
                    lock.unlock();
                }
            }

        },"b");

        Thread c = new Thread(()->{
            while (true) {
                lock.lock();
                try {
                    if (ticket > 0){
                        System.out.println(Thread.currentThread().getName() + " sell " + ticket--);
                    }else break;
                } finally {
                    lock.unlock();
                }
            }

        },"c");

        a.start();
        b.start();
        c.start();
    }
}
