package cn.simon.lock;


/**
 * 演示显式锁和隐式锁的可重入
 */

import java.util.concurrent.locks.ReentrantLock;

/**
 * 结构体如下
 * ObjectMonitor::ObjectMonitor() {
 *   _header       = NULL;
 *   _count       = 0; //有多少个线程获取该锁
 *   _waiters      = 0,
 *   _recursions   = 0;       //线程的重入次数
 *   _object       = NULL;
 *   _owner        = NULL;    //标识拥有该monitor的线程
 *   _WaitSet      = NULL;    //等待线程组成的双向循环链表，_WaitSet是第一个节点
 *   _WaitSetLock  = 0 ;
 *   _Responsible  = NULL ;
 *   _succ         = NULL ;
 *   _cxq          = NULL ;    //多线程竞争锁进入时的单向链表
 *   FreeNext      = NULL ;
 *   _EntryList    = NULL ;    //_owner从该双向循环链表中唤醒线程结点，_EntryList是第一个节点
 *   _SpinFreq     = 0 ;
 *   _SpinClock    = 0 ;
 *   OwnerIsThread = 0 ;
 * }
 */

public class RetryLockDemo03 {

    public static void main(String[] args) {

//        syncRetryLock();

        retryLock();
    }


    /**
     * 隐式锁无需手动解除可重入锁
     * 在我们的ObjectMonitor里面有recursions记录重入次数
     * 每重入一次recursions+1
     * 退出则，则recursions-1
     * 当recursions=0时，说明该线程已经不持有该锁
     */
    public static void syncRetryLock(){
            Object lock = new Object();

            synchronized (lock){
                System.out.println("lock come in 最外层");

                synchronized (lock){
                    System.out.println("lock come in 中间层");
                }
            }
    }
    /**
     * 显式锁需手动解除可重入锁
     * 每一次加锁都对应一次解锁
     * 若没有对应次数解锁则该锁其他线程也无法获取到，代码示例如下
     */
    public static void retryLock(){
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(()->{
            lock.lock();

            System.out.println("t1 lock come in 最外层");

            lock.lock();

            System.out.println("t1 lock come in 中间层");

            lock.unlock();


//        lock.unlock();
        });

        t1.start();


        Thread t2 = new Thread(()->{
            lock.lock();

            System.out.println("t2 lock come in 最外层");

            lock.unlock();
        });
        t2.start();
    }
}
