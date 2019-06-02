package com.web.design.test.singletonTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestSingleton implements Tester {
    @Override
    public int test() {
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class classroom=classLoader.loadClass("tmp.Classroom");
            rate+=25;
            //System.out.println(Thread.currentThread().getContextClassLoader().getClass());

            Method getIns=classroom.getMethod("getInstance");
            if(Modifier.isStatic(getIns.getModifiers()))
                rate+=25;

            Object ins=getIns.invoke(null);
            if(classroom.isInstance(ins))
                rate+=25;

            if(classroom.getConstructors().length==0)
                rate+=25;

            System.out.println(rate);
            return rate;
        } catch (ClassNotFoundException | NoSuchMethodException | ClassCastException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println(rate);
            return rate;
        }
    }
}
