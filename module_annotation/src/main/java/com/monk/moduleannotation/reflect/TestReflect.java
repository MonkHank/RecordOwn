package com.monk.moduleannotation.reflect;


import org.greenrobot.eventbus.Subscribe;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class TestReflect {

  @Subscribe
  void methodSubscribe() {
  }

  /**
   * 测试反射常见api
   */
  public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
    /**===================== 类加载器 ==========================*/
    println("==================类加载器====================");
    // 1.获取一个系统的类加载器(可以获取，当前这个类 ExampleUnitTest 就是它加载的)
    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    println("系统类加载器:" + classLoader);


    // 2.获取系统类加载器的父类加载器（扩展类加载器，可以获取）.
    classLoader = classLoader.getParent();
    println("扩展类加载器:" + classLoader);

    // 3.获取扩展类加载器的父类加载器（引导类加载器，不可获取）.
    classLoader = classLoader.getParent();
    println("引导类加载器:" + classLoader);


    // 4.测试当前类由哪个类加载器进行加载（系统类加载器）:
    classLoader = Class.forName("com.monk.moduleannotation.reflect.ReflectClass").getClassLoader();
    System.out.println("系统类加载器:" + classLoader);
    InputStream in = classLoader.getResourceAsStream("Student.java");
    println("获取文件：" + in);

    //5. 测试 JDK 提供的 Object 类由哪个类加载器负责加载（引导类）
    classLoader = Class.forName("java.lang.Object").getClassLoader();
    println("Object类加载器：" + classLoader);

    /***===================================== 常用API =================================*/

    println("=================== 获取 Class 的三种方式 ==================");
    Class<?> clazz0;
    //1.通过类名
    clazz0 = ReflectClass.class;


    //2.通过对象名，这种方式是用在传进来一个对象，却不知道对象类型的时候使用
//        ReflectClass reflectClass = new ReflectClass();
//        clazz = reflectClass.getClass();
    //上面这个例子的意义不大，因为已经知道 reflectClass 类型是 ReflectClass 类，再这样写就没有必要了
    //如果传进来是一个Object类，这种做法就是应该的
//        Object obj = new ReflectClass();
//        clazz = obj.getClass();


    //3.通过全类名(会抛出异常)
    //一般框架开发中这种用的比较多，因为配置文件中一般配的都是全类名，通过这种方式可以得到Class实例
    Class<?> clazz;
    String className = "com.monk.moduleannotation.reflect.ReflectClass";
    clazz = Class.forName(className);

    println("---1. 类实例");
    println(clazz);

    println("---2. 类实例名字（全类名）");
    println(clazz.getName());

    println("---3. 判断是否为某个类的父类 - isAssignableFrom()");
    println(clazz.isAssignableFrom(clazz0));
    println(clazz.isAssignableFrom(Object.class));

    println("=================== 无参构造和有参构造 ===================");

    ReflectClass newInstance = (ReflectClass) clazz.newInstance();// 默认无参构造函数，没有无参构造函数则会报错
    println(newInstance);

    Constructor<?> constructor = clazz.getConstructor(String.class, int.class);// 有参构造
    ReflectClass nancy = (ReflectClass) constructor.newInstance("nancy", 12);
    println(nancy);

    println("=================== 反射方法 =============================");

    println("---1. 获取指定类所有方法，不含父类方法，private方法也可以获取 - getDeclaredMethods()");
    Method[] methods = clazz.getDeclaredMethods();
    for (Method t : methods) {
      println(t.getName());
    }

    println("---2. 获取包括父类在内的所有方法 - getMethods()");
    Method[] methodsAll = clazz.getMethods();
    for (Method method : methodsAll) {
      println(method.getName());
    }
    Method setAge1 = clazz.getMethod("setAge", int.class, int.class);// 获取指定方法

    println("---3. 获取指定方法，int直接写，不用转Integer - 只获取本类中的方法 getDeclaredMethod(name,parameterTypes...)");
    Method setName = clazz.getDeclaredMethod("setName", String.class);
    Method setAge = clazz.getDeclaredMethod("setAge", int.class, int.class);
    Method getAge = clazz.getMethod("getAge");
    println(setName);
    println(setAge);

    println("---1.1 获取方法修饰符 - getModifiers()");
    for (Method t : methods) {
      println(t.getModifiers());
    }

    println("---1.2 EventBus中的一个运算");
    println("1 & 1 = " + (1 & 1));
    // 值 &  Modifier.ABSTRACT | Modifier.STATIC | 0x40 | 0x1000
    // 值只要是其中一个，值为该值，否则为 0
    int MODIFIERS_IGNORE = Modifier.ABSTRACT | Modifier.STATIC | 0x40 | 0x1000;
    println("1 & MODIFIERS_IGNORE = " + (1 & MODIFIERS_IGNORE));

    println("---1.3 获取方法形参类型，方法没有形参，数组长度为0 - getParameterTypes()");
    for (Method t : methods) {
      Class<?>[] parameterTypes = t.getParameterTypes();
      for (Class<?> p : parameterTypes) {
        println("方法：" + t.getName() + " -> 形参类型：" + p);
      }
    }

    println("---1.4 获取方法指定注解 - getAnnotation(Subscribe.class)");
    for (Method m : methods) {
      Subscribe annotation = m.getAnnotation(Subscribe.class);
      if (annotation != null)
        println(annotation);
    }

    println("---1.5 返回方法的类对象 - getDeclaringClass()");
    for (Method m : methods) {
      Class<?> cs = m.getDeclaringClass();
      println(cs);
    }

    println("---4. 执行方法，需要对象 - invoke(obj,args...)");
    //setName方法返回this(ReflectClass)
    Object jack = setName.invoke(newInstance, "jack");
    //setAge方法返回void
    Object age = setAge.invoke(newInstance, 0, 11);
    Object get = getAge.invoke(newInstance);
    System.out.println("jack = " + jack);
    System.out.println("age = " + age);
    System.out.println("getAge = " + get);
    println(newInstance.toString());

    println("---5. 返回方法形参类型 getGenericParameterTypes ");
    for (Type g : setName.getGenericParameterTypes()) {
      println("setName - type = " + g);
    }
    for (Type g : setAge.getGenericParameterTypes()) {
      println("setAge - type = " + g);
    }

    println("---6. 返回方法形参的注解，二维数组 getParameterAnnotations ");
    Method postLogin = clazz.getDeclaredMethod("postLogin", String.class);
    for (Annotation[] p : postLogin.getParameterAnnotations()) {
      for (Annotation a : p) {
        println("annotation = " + a);
      }
    }

    println("=================== end 反射方法 end=============================\n");

    println("=================== 反射字段 =============================");

    println("---1. 获取所有字段，公有私有，不包含父类 - getDeclaredFields()");
    Field[] fields = clazz.getDeclaredFields();
    for (Field t : fields) {
      t.setAccessible(true);
      print(t.getName() + "：" + t.get(newInstance));
      println("");
    }

    println("---2. 获取指定字段 - getDeclaredField(name)");
    Field name = clazz.getDeclaredField("name");
    println(name.getName());

    println("---3. 使用字段，获取指定对象指定变量的值get()，修改指定对象指定字段的值set() ");
    Object value = name.get(newInstance);
    println(value);
    name.set(newInstance, "jack_set");
    println(name.get(newInstance));

    println("---4. 反射 final 和 static 变量");
    Class<Person> personClass = Person.class;
    final Person person = new Person();

    Field name1 = personClass.getDeclaredField("name");
    name1.setAccessible(true);
    name1.set(person, "xixi");
    println("修改后：" + person.getName());

    Field age1 = personClass.getDeclaredField("age");
    age1.setAccessible(true);
    age1.set(person, 12);
    println("修改后：" + person.age);

    println("修改前：" + person.student);
    Field student = personClass.getDeclaredField("student");
    student.setAccessible(true);
    student.set(person, new Student());
    println("修改后：" + person.student);

    Object o = name1.get(person);
    println("修改后：" + o);// 通过这种方式获取final变量
    println("修改后：" + age1.get(person));

    Field sex = personClass.getDeclaredField("sex");
    sex.setAccessible(true);
    sex.set(null, "男");
    println("修改后：" + sex.get(null));

    println("=================== 反射类泛型 =============================");
    TypeVariable<? extends Class<?>>[] typeParameters = clazz.getTypeParameters();
    for (TypeVariable<? extends Class<?>> t : typeParameters) {
      System.out.println("泛型：" + t);
    }
    System.out.println("泛型的个数：" + clazz.getTypeParameters().length);

    println("=================== 反射接口 =============================");
    Class<?>[] interfaces = clazz.getInterfaces();
    for (Class<?> a : interfaces) {
      System.out.println(a);
    }
    System.out.println(interfaces.length);
  }

  private static void println(Object lineText) {
    System.out.println(lineText);
  }

  private static void print(Object lineText) {
    System.out.print(lineText);
  }


}
