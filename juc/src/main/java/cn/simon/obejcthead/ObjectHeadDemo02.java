package cn.simon.obejcthead;

import org.openjdk.jol.info.ClassLayout;


public class ObjectHeadDemo02 {
    public static void main(String[] args) {
//        System.out.println(VM.current().details());
        //我们对象的填充字节数为8
//        System.out.println(VM.current().objectAlignment());
        Object o = new Object(); //16 bytes
        System.out.println(ClassLayout.parseInstance(o).toPrintable());


        Person p = new Person();
        System.out.println(ClassLayout.parseInstance(p).toPrintable());
    }

}

class Person{ //只有一个对象头的实例对象


    //如果没有下面这些就是只有一个对象头的实例对象
    //如果有就是我们对象包含的实例数据了。
    int id;
    boolean flag;

}
