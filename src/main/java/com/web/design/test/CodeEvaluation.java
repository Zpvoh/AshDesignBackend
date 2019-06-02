package com.web.design.test;

import com.web.design.compiler.Compiler;
import com.web.design.test.combinationTest.TestCombination;
import com.web.design.test.decoratorTest.TestDecorator1;
import com.web.design.test.decoratorTest.TestDecorator2;
import com.web.design.test.decoratorTest.TestDecorator3;
import com.web.design.test.factoryTest.TestFactory1;
import com.web.design.test.factoryTest.TestFactory2;
import com.web.design.test.factoryTest.TestFactory3;
import com.web.design.test.singletonTest.TestSingleton;
import com.web.design.test.strategyTest.TestStrategy1;
import com.web.design.test.strategyTest.TestStrategy2;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeEvaluation {
    public static Tester chooseTester(String designPropName, String sceneName){
        Tester tester=null;
        switch (designPropName){
            case "工厂模式":
                if(sceneName.trim().equals("场景1")){
                    tester= new TestFactory1();
                }else if(sceneName.trim().equals("场景2")){
                    tester= new TestFactory2();
                }else if(sceneName.trim().equals("场景3")){
                    tester=new TestFactory3();
                }
                break;
            case "单例模式":
                tester=new TestSingleton();
                break;
            case "装饰器模式":
                if(sceneName.trim().equals("场景1")){
                    tester= new TestDecorator1();
                }else if(sceneName.trim().equals("场景2")){
                    tester= new TestDecorator2();
                }else if(sceneName.trim().equals("场景3")){
                    tester=new TestDecorator3();
                }
                break;
            case "策略模式":
                if(sceneName.trim().equals("场景1")){
                    tester= new TestStrategy1();
                }else if(sceneName.trim().equals("场景2")){
                    tester= new TestStrategy2();
                }
                break;
            case "组合模式":
                tester=new TestCombination();
                break;
        }

        return tester;
    }

    public static ArrayList<String> extractClassName(ArrayList<String> codes){
        ArrayList<String> codeNames=new ArrayList<>();
        for (String code:
             codes) {
            String[] words=code.trim().split(" ");
            if(words.length>1) {
                int point=0;
                while(!words[point].equals("class") && !words[point].equals("interface")){
                    point++;
                    if(point>=words.length){
                        return null;
                    }
                }

                codeNames.add(words[point+1]);
            }else{
                codeNames.add(null);
            }
        }

        return codeNames;
    }

    public static HashMap<String, Object> evaluate(HashMap<String, Object> scene){
        String designPropName=(String)scene.get("designPropName");
        String sceneName=(String)scene.get("sceneName");
        Compiler compiler=new Compiler();
        ArrayList<String> codeJava=(ArrayList<String>)scene.get("codeJava");
        ArrayList<String> codeNames=extractClassName(codeJava);
        if(codeNames==null) {
            scene.put("testPassRate", "0%");
            return scene;
        }

        Tester tester=chooseTester(designPropName, sceneName);
        Compiler.clearTmp();
        ArrayList<String> codes=new ArrayList<>();
        ArrayList<String> names=new ArrayList<>();

        for(int i=0; i<codeJava.size(); i++){
            if(codeNames.get(i)==null)
                continue;

            if(!codeJava.get(i).trim().split(" ")[0].equals("public")) {
                names.add(codeNames.get(i));
                codes.add("public " + codeJava.get(i));
            }
            else {
                names.add(codeNames.get(i));
                codes.add(codeJava.get(i));
            }
        }

        if(compiler.compiles(names, codes))
            scene.put("testPassRate", String.valueOf(tester.test())+"%");
        else
            scene.put("testPassRate", "0%");

        return scene;
    }
}
