# 反射的一些常见用法

>[参考文章](https://www.cnblogs.com/tech-bird/p/3525336.html)
## 获取方式
```java

Class clazz = null;
//1.通过类名
clazz = ReflectClass.class;


//2.通过对象名，这种方式是用在传进来一个对象，却不知道对象类型的时候使用
//        ReflectClass reflectClass = new ReflectClass();
//        clazz = reflectClass.getClass();
//上面这个例子的意义不大，因为已经知道 reflectClass 类型是 ReflectClass 类，再这样写就没有必要了
//如果传进来是一个Object类，这种做法就是应该的
//        Object obj = new ReflectClass();
//        clazz = obj.getClass();


//3.通过全类名(会抛出异常)
//一般框架开发中这种用的比较多，因为配置文件中一般配的都是全类名，通过这种方式可以得到Class实例
String className = "com.monk.aidldemo.ReflectClass";
clazz = Class.forName(className);
```
## 无参构造和有参构造
```java
// 默认无参构造函数，没有无参构造函数则会报错
ReflectClass newInstance = (ReflectClass) clazz.newInstance();

Constructor constructor = clazz.getConstructor(String.class, int.class);
ReflectClass nancy = (ReflectClass) constructor.newInstance("nancy", 12);
```

## 反射方法
```java
 // 1. 获取指定类所有方法，不含父类方法，private方法也可以获取
Method[] methods = clazz.getDeclaredMethods();
for (Method t : methods) {
    println(t.getName());
}

// 2.获取指定方法，int直接写，不用转Integer
Method setName = clazz.getDeclaredMethod("setName", String.class);
Method setAge = clazz.getDeclaredMethod("setAge", int.class);

// 3. 执行方法，需要有对象
setName.invoke(newInstance, "jack");
setAge.invoke(newInstance, 11);
```

## 反射字段
```java
// 1. 获取所有字段，公有私有，不包含父类
Field[] fields = clazz.getDeclaredFields();
for(Field t : fields){
    println(t.getName());
}

// 2. 获取指定字段
Field name = clazz.getDeclaredField("name");
println(name.getName());

// 3. 使用字段，获取指定对象指定变量的值get()，修改指定对象指定字段的值set()
Object value = name.get(newInstance);
println(value);
name.set(newInstance,"jack_set");
println(name.get(newInstance));
```