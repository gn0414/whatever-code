package cn.simon.volatiles;


/**
 * 双检锁多线程案例
 */

class SingletonObject{
    private static volatile SingletonObject singletonObject;

    private SingletonObject(){};

    public static SingletonObject getInstance(){
        if (singletonObject == null){
            synchronized (SingletonObject.class){
                if (singletonObject == null){
                    //隐患，在多线程情况下，由于重排序，该对象可能还未初始化就就被其他线程读取。
                    singletonObject = new SingletonObject();
                }
            }
        }
        return singletonObject;
    }
}
public class DCLLockDemo {

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            new Thread(()->{
                SingletonObject instance = SingletonObject.getInstance();
                System.out.println(instance);
            }).start();
        }
    }
}
