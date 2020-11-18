package com.ycw;

import service.impl.MyServiceImpl;

public class ByteBuddyTest {
    public static void main(String[] args) {

        //调用MyServiceImpl方法
        MyServiceImpl myService = new MyServiceImpl();
        myService.queryName();
    }
}
