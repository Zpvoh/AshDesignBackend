package com.web.design.test.factoryTest;

import com.web.design.compiler.Compiler;
import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

public class TestFactory1 implements Tester {
    public int test(){
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class circle=classLoader.loadClass("tmp.Circle");
            Class tri=classLoader.loadClass("tmp.Triangle");
            Class square=classLoader.loadClass("tmp.Square");
            Class shape=classLoader.loadClass("tmp.Shape");

            if(shape.isAssignableFrom(circle) && shape.isAssignableFrom(tri) && shape.isAssignableFrom(square)) {
                rate += 25;
            }

            circle.getMethod("draw", String.class);
            rate+=25;
            tri.getMethod("draw", String.class);
            rate+=25;
            square.getMethod("draw", String.class);
            rate+=25;
            System.out.println(rate);
            return rate;
        } catch (ClassNotFoundException | NoSuchMethodException | ClassCastException e) {
            e.printStackTrace();
            System.out.println(rate);
            return rate;
        }
    }
}