package cn.simon.obejcthead;

import org.openjdk.jol.vm.VM;

public class ObjectHeadDemo01 {
    public static void main(String[] args) {
        Object o = new Object(); //new 一个对象,占内存多少

        System.out.println(o.hashCode());//这个hashcode记录在对象的什么地方

        synchronized (o){
            //o对象的对象头mark word会记录一些更为高级锁的信息，例如轻量级、重量级、偏向等等
            //但是底层的互斥实现还是依靠管程来实现互斥的。
        }

        System.gc();//手动收集垃圾,超过15次可以从新生代 -> 老年区


        Customer c1 = new Customer();
        //左边的引用是不是在main栈帧额局部变量表里面
        //引用指针就处于我们的方法区里面的KClass类元信息

    }

}

class Customer{ //只有一个对象头的实例对象


    //如果没有下面这些就是只有一个对象头的实例对象
    //如果有就是我们对象包含的实例数据了。
    int id;
    boolean flag;

}

