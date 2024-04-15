package cn.simon.interrupt;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanInterruptDemo02 {

    static AtomicBoolean isStop = new AtomicBoolean(false);

    public static void main(String[] args) {
        new Thread(()->{
            while (true){
                System.out.println("t1 is working");
                if (isStop.get()){
                    System.out.println(Thread.currentThread().getName()+" has stop");
                    break;
                }
            }

        },"t1").start();

        new Thread(
                ()->{
                    try {Thread.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}
                    isStop.set(true);
                },"t2"
        ).start();
    }
}
