package com.web.design.test.factoryTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestFactory3 implements Tester {
    @Override
    public int test() {
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class mainClass=classLoader.loadClass("tmp.Main");

            Method main=mainClass.getMethod("main", String[].class);
            main.invoke(mainClass.newInstance(), new Object[] { new String[] {} });
            rate+=100;
            System.out.println(rate);
            return rate;
        } catch (ClassNotFoundException | NoSuchMethodException | ClassCastException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println(rate);
            return rate;
        }
    }
}
