package com.web.design.compiler;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class Compiler{
    public static final String PATH_TEST = "com/web/design/test";

    public Compiler() {
    }

    /*public Compiler(String className, String javaCodes) {
        this.className = className;
        this.javaCodes = javaCodes;
        //this.compiledClass = compile(className, javaCodes);
    }*/

    public Class<?> compile(String className, String javaCodes) {
        className = "tmp." + className;
        javaCodes = "package tmp;\n" + javaCodes;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        JavaFileObject fileObject = new InMemoryJavaFileObject(className, javaCodes);
        Iterable<? extends JavaFileObject> files = Arrays.asList(fileObject);
        String flag = "-d";
        String outDir = "";

            File classPath = new File(".");
            outDir = classPath.getAbsolutePath() + File.separator;


        Iterable<String> options = Arrays.asList(flag, outDir);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, files);
        boolean result = task.call();

        return null;
    }

    public boolean compiles(ArrayList<String> classNames, ArrayList<String> javaCodes){
        InMemoryJavaFileObject fileObject=null;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        ArrayList<JavaFileObject> files=new ArrayList<>();
        for(int i=0; i<javaCodes.size(); i++) {
            String className = "tmp." + classNames.get(i);
            String code = "package tmp;\n" + javaCodes.get(i);

            fileObject = new InMemoryJavaFileObject(className, code);
            files.add(fileObject);
        }

        String flag = "-d";
        String outDir = "";

        File classPath = new File(".");
        outDir = classPath.getAbsolutePath() + File.separator;


        Iterable<String> options = Arrays.asList(flag, outDir);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, files);

        return task.call();

    }

    public static boolean clearTmp(){
            File classPath = new File(".");
            String outDir = classPath.getAbsolutePath() + File.separator;
            String classDir = outDir +"tmp";
            return FileUtil.clearFolder(classDir);

    }

    public Object executeTest(String className){
        className = "com.web.design.test." + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            // 生成对象
            Object obj = clazz.newInstance();
            Class<? extends Object> cls = obj.getClass();
            // 调用main方法
            Method m = clazz.getMethod("test");
            Object invoke = m.invoke(obj);
            return invoke;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

class InMemoryJavaFileObject extends SimpleJavaFileObject implements JavaFileObject {
    private String contents = null;

    public InMemoryJavaFileObject(String className, String contents) {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.contents = contents;
    }


    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return contents;
    }
}