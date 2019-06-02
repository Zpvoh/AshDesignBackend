package com.web.design;

import com.web.design.compiler.Compiler;
import com.web.design.test.CodeEvaluation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

@Controller
public class ViewController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    public static void main(String[] args) {
        String code1="public interface Shape {\n" +
                "    public void draw(String color);\n" +
                "}";
        String code2="public class Square implements Shape {\n" +
                "\n" +
                "    public void draw(String color) {\n" +
                "\n" +
                "        System.out.println(\"draw #cc9933 Square on the canvas\");\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "}" ;
        String code3="public class Rectangle implements Shape {\n" +
                "\n" +
                "    public void draw(String color) {\n" +
                "\n" +
                "        System.out.println(\"draw #ff0000 Rectangle on the canvas\");\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "}";
        String code4="public class Circle implements Shape {\n" +
                "\n" +
                "    public void draw(String color) {\n" +
                "\n" +
                "        System.out.println(\"draw #ffcccc Circle on the canvas\");\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "}";
        ArrayList<String> arr=new ArrayList<>();
        arr.add(code1);
        arr.add(code2);
        arr.add(code3);
        arr.add(code4);
        //Compiler.clearTmp();
        Compiler compiler=new Compiler();
//        compiler.compile("Shape", code1);
//        compiler.compile("Square", code2);
//        compiler.compile("Rectangle", code3);
//        compiler.compile("Circle", code4);
        ArrayList<String> names= CodeEvaluation.extractClassName(arr);
        compiler.compiles(names, arr);
        compiler.executeTest("factoryTest.TestFactory1");
    }
}
