package com.ycw;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class MyAgent {
    public static void premain(String agentOps, Instrumentation inst) {
       /* Class clazz=new ByteBuddy().subclass(MyServiceImpl.class)
                .method(ElementMatchers.named("queryName"))
                .intercept()
                .make()            //产生字节码
                .load(ByteBuddyProxy.class.getClassLoader())
                .getLoaded();*/

        //创建一个转换器
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                    TypeDescription typeDescription,
                                                    ClassLoader classLoader,
                                                    JavaModule javaModule) {
                return builder
                        .method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
                        .intercept(MethodDelegation.to(ByteBuddyProxy.class)); // 委托
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {

            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {

            }
        };

//        String packagePrefix = Param.get("prefix") == null ? "com" : Param.get("prefix");
        String packagePrefix="com.ycw.service.impl";
        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith(packagePrefix)
                        .and(ElementMatchers.not(ElementMatchers.isAnnotation()))
                        .and(ElementMatchers.not(ElementMatchers.isInterface()))
                        .and(ElementMatchers.not(ElementMatchers.isEnum()))) // 指定需要拦截的类
                .transform(transformer)
                .with(listener)
                .installOn(inst);

    }
}
