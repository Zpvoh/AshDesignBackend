package com.web.design.test.decoratorTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestDecorator3 implements Tester {
    @Override
    public int test() {
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class shapeDecorator=classLoader.loadClass("tmp.ShapeDecorator");
            Class redDec=classLoader.loadClass("tmp.RedShapeDecorator");
            rate+=25;

            if(redDec.getSuperclass().equals(shapeDecorator))
                rate+=25;

            Method draw=shapeDecorator.getMethod("draw");
            rate+=25;

            Method set=redDec.getMethod("setRed");
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
