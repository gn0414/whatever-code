package cn.simon.threadlocal;


import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

class MyObject{

    //这个方法一般不需要复写,只是作为演示
    @Override
    protected void finalize() throws Throwable {
        System.out.println("invoke.... final");
    }
}
public class ThreadLocalDemo03 {

    public static void main(String[] args) {
//        strongReference();
//        softReference();
//        weakReference();
        phantomReference();



    }

    public static void phantomReference() {

        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);

        System.out.println("虚引用get " + phantomReference.get());

        List<byte[]> data = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                Reference<? extends MyObject> poll = referenceQueue.poll();
                if (poll != null) {
                    System.out.println("----有虚对象被回收加入了队列");
                    break;
                }
            }
        }).start();

        new Thread(()->{
            while (true){
                data.add(new byte[1024*1024 * 1000]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("list add ok");
            }
        }).start();

    }

    public static void weakReference(){

        WeakReference<MyObject> myObjectWeakReference = new WeakReference<>(new MyObject());

        System.out.println("gc before "+myObjectWeakReference.get());

        System.gc();//人工开启gc

        try {Thread.sleep(1000);} catch (InterruptedException e) {throw new RuntimeException(e);}

        System.out.println("gc after 内存足够 "+myObjectWeakReference.get());


    }
    public static void softReference(){

        SoftReference<MyObject> myObjectSoftReference = new SoftReference<>(new MyObject());

        System.out.println("gc before "+myObjectSoftReference.get());

        System.gc();//人工开启gc

        System.out.println("gc after 内存足够 "+myObjectSoftReference.get());


    }
    public static void strongReference(){
        MyObject myObject = new MyObject();

        System.out.println("gc before "+myObject);

        myObject = null;
        //MyObject对象没有被引用了
        System.out.println("gc after "+myObject);

        System.gc();//人工开启gc
    }



}
