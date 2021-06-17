package com.hyd.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TargetTest {
    public static void main(String[] args) {
        final Target target = new Target();
        final Advice advice = new Advice();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                advice.before();
                Object invoke = method.invoke(target, objects);
                advice.after();
                return invoke;
            }
        });

        Target proxy = (Target) enhancer.create();
        proxy.save();
    }
}
