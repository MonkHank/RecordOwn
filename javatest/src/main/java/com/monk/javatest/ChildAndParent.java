package com.monk.javatest;

/**
 * @author monk
 * @date 2019/7/17
 */
public class ChildAndParent {

    /** =================== 接口继承，与java 不能出现同名同参方法的原因 ==================**/
    public interface IParent {
        void a(boolean b);

        void b();
    }

    public interface IChild extends IParent {
        void c(String string);
    }

    class Child implements IChild {
        @Override
        public void a(boolean b) {
        }

        @Override
        public void b() {
        }

        @Override
        public void c(String string) {
        }
    }


    /**
     * 重点是虽然没有实现 IParent，不过已对其内部方法的实现
     */
    class Parent {
        public void a(boolean b){}
        public void b() {
        }
    }
//    上面那个就相当于这个，解释了为何java 中不能有同名同参函数
//    class Parent implements IParent{
//        @Override
//        public void a(boolean b) {
//        }
//        @Override
//        public void b() {
//        }
//    }

    /**
     * 对于没有继承父接口的父类已对父接口方法声明，只需强制实现子接口方法
     */
    class child extends Parent implements IChild{
        @Override
        public void c(String text) {
        }
    }
}
