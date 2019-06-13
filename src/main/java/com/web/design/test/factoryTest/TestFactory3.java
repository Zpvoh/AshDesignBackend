package com.web.design.test.factoryTest;

import com.web.design.compiler.ServiceClassLoader;
import com.web.design.test.Tester;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestFactory3 implements Tester {
    @Override
    public int test() {
        int rate=0;
        try {
            ClassLoader classLoader=new ServiceClassLoader("./");
            Class mainClass=classLoader.loadClass("tmp.Main");

            Method main=mainClass.getMethod("main", String[].class);
            PrintStream printStream=new PrintStream(new FileOutputStream("tmp.file"));
            PrintStream originStream=System.out;
            System.setOut(printStream);
            main.invoke(mainClass.newInstance(), new Object[] { new String[] {} });
            System.setOut(originStream);
            FileInputStream fileInputStream=new FileInputStream("tmp.file");
            Scanner scanner=new Scanner(fileInputStream);
            StringBuilder output=new StringBuilder();
            while(scanner.hasNext()){
                output.append(scanner.nextLine());
                output.append("\n");
            }
            if(output.toString().split(" ")[0].equals("draw")) {
                rate += 100;
            }
            System.out.println(rate);
            return rate;
        } catch (ClassNotFoundException | NoSuchMethodException | ClassCastException | InstantiationException | IllegalAccessException | InvocationTargetException | FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(rate);
            return rate;
        }
    }
}
