package com.simon.annotation;

public class Child extends Parent{

    //一旦你给某一个方法标注了override注解,意味着子类只能按照规则来重写
    //从而影响到程序的运行
    @Override
    public void test() {
        super.test();
    }

}
