package com.web.design.test.factoryTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestFactory2 implements Tester {
    @Override
    public int test() {
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class circle=classLoader.loadClass("tmp.Circle");
            Class tri=classLoader.loadClass("tmp.Triangle");
            Class square=classLoader.loadClass("tmp.Square");
            Class shapeFactory=classLoader.loadClass("tmp.ShapeFactory");

            Method getShape=shapeFactory.getMethod("get_shape", String.class);
            Object factory=shapeFactory.newInstance();
            Object circleIns=getShape.invoke(factory, "Circle");
            if(circle.isInstance(circleIns))
                rate+=33;

            Object triIns=getShape.invoke(factory, "Triangle");
            if(tri.isInstance(triIns))
                rate+=34;

            Object squareIns=getShape.invoke(factory, "Square");
            if(square.isInstance(squareIns))
                rate+=33;

            System.out.println(rate);
            return rate;
        } catch (ClassNotFoundException | NoSuchMethodException | ClassCastException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println(rate);
            return rate;
        }
    }
}
