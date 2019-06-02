package com.web.design.test.decoratorTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestDecorator2 implements Tester {
    @Override
    public int test() {
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class shapeDecorator=classLoader.loadClass("tmp.ShapeDecorator");
            rate+=25;

            Method draw=shapeDecorator.getMethod("draw");
            rate+=25;
            if(Modifier.isAbstract(shapeDecorator.getModifiers()))
                rate+=25;

            if(Modifier.isAbstract(draw.getModifiers()))
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
