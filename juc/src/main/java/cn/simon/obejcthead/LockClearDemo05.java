package cn.simon.obejcthead;

public class LockClearDemo05 {

    static Object objectLock = new Object();

    public void m1(){

//        synchronized (objectLock){
//            System.out.println("------hello 锁消除");
//        }

        //锁消除,JIT编译器会无视它
        //这个代码过于傻逼我就不解释了
        Object o = new Object();
        synchronized (o){
            System.out.println("-----------hello 锁消除 "+o.hashCode()+" right lock is "+objectLock.hashCode());
        }
    }

    public static void main(String[] args) {

        LockClearDemo05 lockClearDemo = new LockClearDemo05();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                lockClearDemo.m1();
            },String.valueOf(i)).start();
        }
    }
}
