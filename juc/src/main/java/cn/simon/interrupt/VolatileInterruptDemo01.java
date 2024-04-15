package cn.simon.interrupt;

public class VolatileInterruptDemo01 {

    static volatile  boolean isStop = false;

    public static void main(String[] args) {


        new Thread(()->{
            while (true){
                System.out.println("t1 is working");
                if (isStop){
                    System.out.println(Thread.currentThread().getName()+" has stop");
                    break;
                }
            }

        },"t1").start();

        new Thread(
                ()->{
                    try {Thread.sleep(2);} catch (InterruptedException e) {throw new RuntimeException(e);}
                    isStop = true;
                },"t2"
        ).start();
    }
}
