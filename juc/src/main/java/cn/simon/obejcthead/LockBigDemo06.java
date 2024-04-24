package cn.simon.obejcthead;

public class LockBigDemo06 {


    static  Object objectLock = new Object();
    public static void main(String[] args) {

        new Thread(()->{
            synchronized (objectLock){
                System.out.println("111111");
            }
            synchronized (objectLock){
                System.out.println("222222");
            }
            synchronized (objectLock){
                System.out.println("333333");
            }
            synchronized (objectLock){
                System.out.println("444444");
            }

        },"t1").start();


    }
}
