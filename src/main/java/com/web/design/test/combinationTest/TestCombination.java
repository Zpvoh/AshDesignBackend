package com.web.design.test.combinationTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestCombination implements Tester {
    @Override
    public int test() {
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class<?> employee=classLoader.loadClass("tmp.Employee");

            Field subs=employee.getDeclaredField("subordinates");
            if(subs.getType().equals(Array.newInstance(classLoader.loadClass("tmp.Employee"), 1).getClass()))
                rate+=25;

            employee.getMethod("add", employee);
            rate+=25;

            employee.getMethod("getSubordinates");
            rate+=25;

            Method toString=employee.getMethod("toString");
            String str=(String)toString.invoke(employee.getConstructor
                    (String.class, String.class, int.class).newInstance("name", "dept", 100));
            if(str.split(" ")[0].equals("Employee")) {
                rate += 25;
            }

            System.out.println(rate);
            return rate;
        } catch (ClassNotFoundException | NoSuchMethodException | ClassCastException | NoSuchFieldException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println(rate);
            return rate;
        }
    }
}
