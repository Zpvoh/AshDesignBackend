package com.web.design.test.decoratorTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

public class TestDecorator1 implements Tester {
    @Override
    public int test(){
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class circle=classLoader.loadClass("tmp.Circle");
            Class rect=classLoader.loadClass("tmp.Triangle");
            Class square=classLoader.loadClass("tmp.Square");
            Class shape=classLoader.loadClass("tmp.Shape");

            circle.asSubclass(shape);
            rect.asSubclass(shape);
            square.asSubclass(shape);
            if(shape.isAssignableFrom(circle) && shape.isAssignableFrom(rect) && shape.isAssignableFrom(square)) {
                rate += 25;
            }

            circle.getMethod("draw");
            rate+=25;
            rect.getMethod("draw");
            rate+=25;
            square.getMethod("draw");
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
