package com.ycw;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyProxy implements InvocationHandler {

    private Object object;

    public MyProxy(Object object){
        this.object=object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        long start=System.currentTimeMillis();

        Object res = method.invoke(object, args);

        long end=System.currentTimeMillis();

        System.out.println("方法调用耗时："+(end-start));

        return res;
    }
}
