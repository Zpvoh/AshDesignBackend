# Blockly pj 说明文档
                                                                          成员：邱轶扬、安晨鑫、王国镔、崔帅帅

## Part 1 项目组织以及其中每个文件的说明
* ### 前端项目组织  
         │  ├─bean   # 与后端交互数据相关类
         │  ├─blockly-learn  # 设计模式页面
         │  ├─blockly-program # 设计模式场景
         │  │  └─service
         │  ├─core   # 核心文件 路由和material
         │  ├─dialog # 弹窗组件作为其他的组件的子组件调用
         │  ├─footer # 页脚
         │  ├─header #导航
         │  ├─history-table # 学习记录表格
         │  │  └─service
         │  ├─index # 主页
         │  │  └─service
         │  ├─login # 登陆
         │  │  └─service
         │  ├─overview-root # 管理员页面
         │  │  └─service
         │  ├─quick-start # 新手导航页面
         │  ├─register # 注册
         │  │  └─service
         │  └─user-info # 个人信息页面
         ├─assets
         ├─data # 前端的一些常量值
         ├─environments
         ├─js
         │  ├─blocks # blockly的自定义块文件和块转java代码文件
         │  ├─languages # blockly的支持编码文件
         │  │  └─js
         │  └─media # blockly界面的音频文件
         └─pic # 图片

* ### 前端主要文件说明
文件名|说明
:---:|:---:
app.module.ts|根模块，在其中声明和导入其他模块
dialogData.ts|弹窗组件的输入对象
sceneHistory.ts|历史场景对象
user.ts|用户信息对象
app.routing.module.ts|配置相关路由
material.module.ts|由angular material 提供的组件
chart.ts| 折线图绘制相关操作
HtmlPipe.ts| html管道相关操作
particles-config.ts|particleJS的配置
introductionData.ts|场景题目
toolbox.ts| 工具箱
其他组件中的文件|参照目录说明

* ### 后端项目组织 

``` python
├── java
│   └── com
│       └── web
│           └── design
│               ├── CompilerController.java #该为编译行为控制器
│               ├── DesignApplication.java
│               ├── SceneController.java #该为场景记录修改控制器
│               ├── TokenUtil.java
│               ├── UserController.java #该为用户信息操作控制器
│               ├── cipher
│               │   ├── JWT.java #token加密
│               │   └── PasswordEncoder.java #密码加密
│               ├── compiler #后台代码编译行为包
│               │   ├── Compiler.java
│               │   ├── Executor.java
│               │   ├── FileUtil.java
│               │   └── ServiceClassLoader.java
│               ├── mapper
│               │   ├── SceneMapper.java
│               │   └── UserMapper.java
│               └── test #此处存放测试用例与各类评估
│                   ├── CodeEvaluation.java
│                   ├── Tester.java
│                   ├── combinationTest
│                   │   └── TestCombination.java
│                   ├── decoratorTest
│                   │   ├── TestDecorator1.java
│                   │   ├── TestDecorator2.java
│                   │   └── TestDecorator3.java
│                   ├── factoryTest
│                   │   ├── TestFactory1.java
│                   │   ├── TestFactory2.java
│                   │   └── TestFactory3.java
│                   ├── singletonTest
│                   │   └── TestSingleton.java
│                   └── strategyTest
│                       ├── TestStrategy1.java
│                       └── TestStrategy2.java
└── resources
    ├── application.properties #应用属性
    ├── static
    │   ├── SceneMapper.xml #Mybatis静态映射
    │   └── UserMapper.xml
    └── templates

```

## Part 2 关键功能实现的细节
* ### angular 前端技术
    1. 引入Particles.js增强UI  
        我们可以为了炫酷的效果牺牲一些性能，但是登录完成后它的任务也就结束了，所以必须销毁它，以免对应用产生性能影响。  
        ```typescript
        ngOnInit() {
             particlesJS('particles', Settings.ParticlesConfig); 
         }  
         ngOnDestroy() {
                 if (pJSDom && pJSDom.length > 0) {
                     // 单页应用的全局对象一直会存在，会产生性能问题
                     // 如果存在全局对象则先销毁
                     pJSDom[0].pJS.fn.vendors.destroypJS();
                     pJSDom = [];
                 }
             }
        ```
    
     1. Treat LocalStorage As A Cache  
         localstorage应该起到缓存的作用加速用户体验。  
         Step 1: 在登陆时比对用户场景数量是否和localStorage一致
                 如果一致 不做操作，如果不一致，更新localStorage      
         Step 2: 在获取操作记录时直接访问localStorage  
         
         利用好localStorage可以减少不必要的后台交互，不必每次查看历史记录都从后台拿到，显著增加交互的流畅性

     2. 使用pipe和innerHTML 显示html文本
         Angular 中有 innerHTML 属性来设置要显示的内容
         但是如果内容包含 CSS 样式，无法显示样式的效果。
         
         Angular默认把所有值都当做不可信任的。 当值从模板中以属性（Property）、DOM元素属性（Attribte)、CSS类绑定或插值表达式等途径插入到DOM中的时候， Angular将对这些值进行无害化处理（Sanitize），对不可信的值进行编码。
         自定义一个 Pipe 来对内容做转换。  
         ```typescript
         @Pipe({
              name: "html"
          })
          export class HtmlPipe implements PipeTransform{
              constructor (private sanitizer: DomSanitizer) {
              }
              transform(style) {
                  return this.sanitizer.bypassSecurityTrustHtml(style);
              }
          }
         ```
     3. angular中引入canvas和使用three.js  
         在组件类中，我们可以使用@ViewChild()装饰器向画布注入引用。组件初始化后，我们就可以访问Canvas DOM节点以及其绘图上下文
         注意一定要在 ngAfterViewInit 函数之后获取否则可能得到的值是undefined.  
         
          ```typescript
          <canvas #mycanvas class="mycanvas"></canvas>
            // ts
            @ViewChild('mycanvas') canvasRef: ElementRef;
            let canvas: htmlElement= this.canvasRef.nativeElement;
             // 创建scene
             this.scene = new THREE.Scene();
             // 设置背景色
             this.scene.background = new THREE.Color(0xffffff);
             // 设置相机
             this.camera = new THREE.PerspectiveCamera(30,
                        canvas.clientWidth / canvas.clientHeight, 1, 1000);
             this.camera.position.set(100, 300, 50);//设置相机位置
             this.camera.lookAt(new THREE.Vector3(0, 0, 0));//让相机指向原点
             // 往场景里添加不同的模型
             ···
          ```
* ####blockly 前端技术
    1. 实现自定义blockly块  
        利用Google Blockly 的官方文档中的接口，结合自行设计的场景进行块的自定义。
        ```typescript
        Blockly.Blocks['factory_interface_1'] = {
            init: function() {
                this.appendDummyInput()
                    .appendField("interface Shape");
                this.appendStatementInput("ABSTRACT")
                    .setCheck('factory_interface_method')
                    .appendField("method");
                this.appendStatementInput("IMPLIMENT_1")
                    .setCheck('factory_class_1')
                    .appendField("impliments");
                this.appendStatementInput("IMPLIMENT_2")
                    .setCheck('factory_class_1');
                this.appendStatementInput("IMPLIMENT_3")
                    .setCheck('factory_class_1');
                this.setColour(0);
                this.setTooltip("");
                this.setHelpUrl("");
            }
        };
        ```
    2. 实现自定义blockly块与java代码的转译  
        Google Blockly 官方只提供了JavaScript、Python、PHP和Lua的转译接口，并没有我们所需要的Java接口。在阅读源码后发现转译的本质是
        定义一系列的解析规则后将块变为字符串进行拼接。因此我们自行实现了Java转译文件。
        ```javascript
        Blockly.JavaScript['factory_interface_1'] = function (block) {
            const text_name = "Shape";
            const statements_abstract = Blockly.JavaScript.statementToCode(block, 'ABSTRACT');
            const statements_impliment_1 = Blockly.JavaScript.statementToCode(block, 'IMPLIMENT_1');
            const statements_impliment_2 = Blockly.JavaScript.statementToCode(block, 'IMPLIMENT_2');
            const statements_impliment_3 = Blockly.JavaScript.statementToCode(block, 'IMPLIMENT_3');
            let code = '';
            let codeList = '';
            code += 'interface ' + text_name + ' {\n\n' + indent(statements_abstract) + '}\n\n';
            codeList += code + '$';
            code += indent(statements_impliment_1) + indent(statements_impliment_2) + indent(statements_impliment_3);
            if (statements_impliment_1.length > 0) {
                codeList += statements_impliment_1 + '$';
            }
            if (statements_impliment_2.length > 0) {
                codeList += statements_impliment_2 + '$';
            }
            if (statements_impliment_3.length > 0) {
                codeList += statements_impliment_3 + '$';
            }
            localStorage.setItem("code", codeList);
            return code;
        };
        ```
    3. 自定义工具箱  
        利用XML语法自定义页面中的工具箱
        
        ```xml
        <xml xmlns="http://www.w3.org/1999/xhtml" id="toolbox" style="display: none;">
            <category name="factory_1" colour="#4C97FF" secondaryColour="#3373CC">
                <block type="factory_interface_1" id=""></block>
                <block type="factory_class_1" id=""></block>
                <block type="factory_interface_method" id=""></block>
                <block type="factory_method_1" id=""></block>
            </category>
        </xml>
        ```
    4. 更改官方设定的样式  
        Google Blokcly 没有提供更改显示样式的更改接口，我们在研究官方库文件后发现整个Blockly系统在浏览器窗口进行显示时是使用树形结构将
        内容插入到dom树中进行显示，并进一步在库文件中发现了官方设定样式的方式。  
        Google官方将一些固定的样式写入一个样式数组通过类在相应节点添加样式
        ```javascript
        Blockly.Css.CONTENT = [".blocklySvg {", "background-color: #fff;", "outline: none;", "overflow: hidden;", 
        "position: absolute;", "display: block;", "}", ".blocklyWidgetDiv {", "display: none;", "position: absolute;", 
        "z-index: 99999;", "}", ".injectionDiv {", "height: 100%;", "position: relative;", "overflow: hidden;", 
        "touch-action: none", "}", ".blocklyNonSelectable {", "user-select: none;", "-moz-user-select: none;", 
        "-ms-user-select: none;", "-webkit-user-select: none;", "}", ".blocklyWsDragSurface {", "display: none;",
        ......];
        ```
        我们更改了一些特定类的css，使其对应节点外观更美观。  
        除此以外，我们还改变了一些dom树的生成，更改了生成的内容，添加进了自己设置的模块。
        ```javascript
        goog.ui.tree.BaseNode.prototype.getRowSafeHtml = function () {
          var a = {};
          a["padding-" + (this.isRightToLeft() ? "right" : "left")] = this.getPixelIndent_() + "px";
          a = {"class": this.getRowClassName(), style: a};
          var b = [this.getExpandIconSafeHtml(), this.getIconSafeHtml(), this.getLabelSafeHtml(), this.getLabelSafeself()];
          return goog.html.SafeHtml.create("div", a, b)
        };
        goog.ui.tree.BaseNode.prototype.getLabelSafeself = function () {
          var a = goog.html.SafeHtml.create("div", {
            "class": "myStyle",
            title: this.getToolTip(),
          });
          return goog.html.SafeHtml.concat(a, '')
        };
        ```
* ### 后端技术

    后端主要实现了登录与注册功能（Spring Boot & MyBatis xml实现），学习记录查询与持久化（Spring Boot & MyBatis xml实现)以及代码在线编译与正确性测试(Java动态编译）

    后端技术的主要重心在于代码在线编译与正确性测试，我们使用了基于Java反射机制的动态编译的策略解决该问题。

    1. JavaCompiler类编译  
        通过设置classpath和输入JavaFileObject集合对象编译
        
        
        自定义实现JavaFileObject接口的类InMemoryJavaFileObject以实现内存中代码编译

        具体代码如下所示：

        ```java
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
        ```

        类实现如下所示

        ```java
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
        ```

    2. 代码正确性判断：基于反射机制的用例设计
        代码正确性判断的过程是基于反射机制的，通过判断编译完成的类中是否有某些方法的存在，或者是某些属性是否正确，或者在调用后是否与预测相符。

        为此我们抽象了一个Tester接口，以代表其为测试用例类，在测试时，我们只要调用不同测试用例的test接口函数即可进行测试。具体实现如下所示
        ```java
        package com.web.design.test;

        public interface Tester {
            public int test();
        }
        ```

        组合模式测试用例如下
        ```java
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
        ```

    3. 对无法重复编译问题的解决

        在project编写的过程之中，我们遇到了同一场景只能编译一次的怪现象，后来发现问题在于ClassLoader默认的cache存储了类，会导致重新编译的代码无法从外存读入JVM。
        解决方案：自定义ClassLoader类，每次用例运行都使用不同的ClassLoader对象。如下所示

        ```java
        public class ServiceClassLoader extends ClassLoader{
            private String classPath;
            public ServiceClassLoader(String classPath) {
                this.classPath = classPath;
            }
            /**
            * 重写父类的findClass 方法。 父类的loadClass会调用此方法
            */
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                Class<?> c = null;
                byte[] classData = getClassData(name);
                if (classData!=null) {
                    c = defineClass(name, classData, 0, classData.length);
                }else {
                    throw new ClassNotFoundException();
                }
                return c;
            }
            private byte[] getClassData(String name) {

                String path = classPath + "/"+ name.replace('.', '/') + ".class";

                InputStream iStream = null;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    iStream = new FileInputStream(path);

                    byte[] buffer = new byte[1024];
                    int temp = 0;
                    while ((temp = iStream.read(buffer))!=-1) {
                        byteArrayOutputStream.write(buffer, 0, temp);
                    }
                    if (byteArrayOutputStream!=null) {
                        return byteArrayOutputStream.toByteArray();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (iStream!=null) {
                            iStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (byteArrayOutputStream!=null) {
                            byteArrayOutputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }

        ```
