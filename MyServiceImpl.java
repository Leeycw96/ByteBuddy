package service.impl;

import com.ycw.MyProxy;
import service.MyService;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyServiceImpl implements MyService {

    @Override
    public List<String> queryName(){
        System.out.println("query start");
        List<String> lists=new ArrayList<String>();
        for(int i=0;i<10;++i){
            lists.add(UUID.randomUUID().toString());
        }
        System.out.println("query end");
        return lists;
    }

    public static void main(String[] args) {
        MyService myService = new MyServiceImpl();

        MyProxy myProxy=new MyProxy(myService);
        MyService serviceProxy= (MyService) Proxy.newProxyInstance(myService.getClass().getClassLoader(), myService.getClass().getInterfaces(),myProxy);

        serviceProxy.queryName();


    }
}
