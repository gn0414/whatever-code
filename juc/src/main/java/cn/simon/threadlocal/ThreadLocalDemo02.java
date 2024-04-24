package cn.simon.threadlocal;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 必须回收自定义的ThreadLocal变量，尤其是在线程池场景下，线程经常会复用，
 * 如果不清理自定义的ThreadLocal变量，可能会影响后续业务逻辑和内存泄露的问题。尽量
 * 在代码里面使用try-finally回收
 *
 * 内存泄露demo
 */

class MyData{
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(()->0);

    public void add(){
        threadLocal.set(threadLocal.get()+1);
    }
}
public class ThreadLocalDemo02 {

    public static void main(String[] args) {
        MyData myData = new MyData();

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {

            for (int i = 0; i < 10; i++) {
                threadPool.submit(()->{
                    try {
                        Integer before = myData.threadLocal.get();
                        myData.add();
                        Integer after = myData.threadLocal.get();
                        System.out.println("before: "+before + " after: "+after);
                    }finally {
//                        myData.threadLocal.remove();

                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
