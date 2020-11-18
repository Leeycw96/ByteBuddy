package com.ycw;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import service.MyService;
import service.impl.MyServiceImpl;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ByteBuddyProxy {

    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
        String methodName = method.getName();
        if (methodName.startsWith("equals")
                || methodName.startsWith("hashCode")
                || methodName.startsWith("toString")) {
            return callable.call();
        }
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


        long start = System.currentTimeMillis();
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            Long elpse = System.currentTimeMillis() - start;
            System.out.println(elpse);
            //LogCachePool.putInstant(method.getDeclaringClass().getName(), methodName, elpse);
        }
    }



}
