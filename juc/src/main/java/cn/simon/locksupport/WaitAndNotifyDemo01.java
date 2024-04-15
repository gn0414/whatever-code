package cn.simon.locksupport;

public class WaitAndNotifyDemo01 {

    public static void main(String[] args) {
        Object lock = new Object();

//        m1(lock);

//        error1(lock);

        error2(lock);
    }

    public static void m1(Object lock){
        Thread t1 = new Thread(
                ()->{
                    synchronized (lock){
                        System.out.println(Thread.currentThread().getName() + "come in");

                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(Thread.currentThread().getName() + "被唤醒");
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
                    synchronized (lock){
                        lock.notify();
                        System.out.println(Thread.currentThread().getName() + "发出通知");
                    }
                },"t2"
        );
        t2.start();
    }

    /**
     * wait 和 notify只能在synchronized同步块里面进行使用
     * @param lock 锁对象
     */
    public static void error1(Object lock){
        Thread t1 = new Thread(
                ()->{
//                    synchronized (lock){
                        System.out.println(Thread.currentThread().getName() + "come in");

                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(Thread.currentThread().getName() + "被唤醒");
//                    }
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
//                    synchronized (lock){
                        lock.notify();
                        System.out.println(Thread.currentThread().getName() + "发出通知");
//                    }
                },"t2"
        );
        t2.start();
    }

    /**
     * wait和notify的使用顺序一定要先wait在notify,否则无法唤醒
     * @param lock 锁对象
     *
     */
    public static void error2(Object lock){
        Thread t1 = new Thread(
                ()->{
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (lock){
                    System.out.println(Thread.currentThread().getName() + "come in");

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + "被唤醒");
                    }
                },"t1"
        );
        t1.start();



        Thread t2 = new Thread(
                ()->{
                    synchronized (lock){
                    lock.notify();
                    System.out.println(Thread.currentThread().getName() + "发出通知");
                    }
                },"t2"
        );
        t2.start();
    }
}
