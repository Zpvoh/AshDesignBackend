package com.web.design.test.strategyTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestStrategy1 implements Tester {
    @Override
    public int test() {
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class stra=classLoader.loadClass("tmp.Strategy");
            rate+=25;

            Class add=classLoader.loadClass("tmp.OperationAdd");
            if(stra.isAssignableFrom(add))
                rate+=12;
            Method opAdd=add.getMethod("doOperation", int.class, int.class);
            Integer r=(Integer)opAdd.invoke(add.newInstance(), 2, 3);
            if(r==5)
                rate+=13;

            Class sub=classLoader.loadClass("tmp.OperationSubstract");
            if(stra.isAssignableFrom(sub))
                rate+=12;
            Method opSub=sub.getMethod("doOperation", int.class, int.class);
            r=(Integer)opSub.invoke(sub.newInstance(), 3, 2);
            if(r==1)
                rate+=13;

            Class mult=classLoader.loadClass("tmp.OperationMultiply");
            if(stra.isAssignableFrom(mult))
                rate+=12;
            Method opMult=mult.getMethod("doOperation", int.class, int.class);
            r=(Integer)opMult.invoke(mult.newInstance(), 3, 2);
            if(r==6)
                rate+=13;

            System.out.println(rate);
            return rate;
        } catch (ClassNotFoundException | NoSuchMethodException | ClassCastException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println(rate);
            return rate;
        }
    }
}
