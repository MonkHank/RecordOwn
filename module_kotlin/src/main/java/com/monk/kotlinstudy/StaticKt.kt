package com.monk.kotlinstudy

import com.monk.kotlinstudy.extends.Student
import javax.xml.ws.Holder

// kotlin中默认类都是final，想要实现继承关系，用open，方法亦是如此
class StaticKt {

    /*** 饿汉式*/
    object SingleDemo

    object SingleDemoCopy {
        fun doAction() {}
    }

    /*** 懒汉式*/
    class SingleDemo2 private constructor() {
        companion object {
            private var instance: SingleDemo2? = null
                get() {// 查看java文件，内部对应一个get()函数
                    if (field == null) field = SingleDemo2() // field 幕后属性、后备属性
                    return field
                }

            fun get(): SingleDemo2 {
                return instance!!
            }

        }
    }

    /*** 线程安全的懒汉式*/
    class SingleDemo3 private constructor() {
        companion object {
            private var instance: SingleDemo3? = null
                get() {
                    if (field == null) field = SingleDemo3()
                    return field
                }

            @Synchronized
            fun get(): SingleDemo3 {
                return instance!!
            }
        }
    }

    /*** 双重校验锁式*/
    class SingleDemo4 private constructor() {
        companion object {
            val instance: SingleDemo4 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { SingleDemo4() }
        }
    }

    class SingleDemo4Two private constructor(private val property: Int) {
        //这里可以根据实际需求发生改变
        companion object {
            @Volatile
            private var instance: SingleDemo4Two? = null

            fun getInstance(property: Int) = instance ?: synchronized(this) {
                instance ?: SingleDemo4Two(property).also { instance = it }
            }
        }
    }

    /*** 静态内部类*/
    class SingleDemo5 private constructor() {
        companion object {
            val instance = Holder.holder
        }

        private object Holder {
            val holder = SingleDemo5()
        }
    }

    fun doAction1() {}

    // 延迟加载的效果
    private lateinit var student: Student

    fun doAction4() {
        student = Student()

        println(student)
    }

    companion object {
        // 静态内部类非静态方法
        fun doAction2() {}

        // 真正的静态，会在StaticKt生成一个静态方法，同时也会在静态内部类生成一个非静态方法
        @JvmStatic
        fun doAction3(): Int {
            return -1
        }

        @JvmStatic
        fun main(args: Array<String>) {
            StaticKt.doAction2()
            StaticKt.doAction3()

            val kt = StaticKt()
            kt.doAction1()

            for (i in 0..1) kt.doAction4()
        }
    }

}
