package com.web.design.test.strategyTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestStrategy2 implements Tester {
    @Override
    public int test() {
        int rate = 0;
        try {
            ClassLoader classLoader = new ServiceClassLoader("./");
            Class context = classLoader.loadClass("tmp.Context");
            rate += 20;

            Class stra = classLoader.loadClass("tmp.Strategy");
            Constructor con = context.getConstructor(stra);
            rate += 20;

            Method exec = context.getMethod("executeStrategy", int.class, int.class);

            Class add = classLoader.loadClass("tmp.OperationAdd");
            Object contextAdd = con.newInstance(add.getConstructor().newInstance());
            Integer r = (Integer) exec.invoke(contextAdd, 3, 2);
            if (r == 5)
                rate += 20;

            Class sub = classLoader.loadClass("tmp.OperationSubstract");
            Object contextSub = con.newInstance(sub.getConstructor().newInstance());
            r = (Integer) exec.invoke(contextSub, 3, 2);
            if (r == 1)
                rate += 20;

            Class mul = classLoader.loadClass("tmp.OperationMultiply");
            Object contextMul = con.newInstance(mul.getConstructor().newInstance());
            r = (Integer) exec.invoke(contextMul, 3, 2);
            if (r == 6)
                rate += 20;

            System.out.println(rate);
            return rate;
        } catch (ClassNotFoundException | NoSuchMethodException | ClassCastException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println(rate);
            return rate;
        }
    }
}
