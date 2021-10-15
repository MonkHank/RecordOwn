package com.monk.kotlinstudy

import kotlin.reflect.KProperty

// https://blog.csdn.net/wzgiceman/article/details/82689135
class By {

    interface Base {
        fun show()
    }

    open class BaseImpl:Base{
        override fun show() {
            println("BaseImpl::show()")
        }
    }

    // 1. 类代理
    // 委托模式已已经证明是实现继承的一个很好的替代方式
    class BaseProxy(base:Base):Base by base{
        fun otherShow(){
            println("BaseProxy::otherShow()")
        }
    }


    //2. 自定义委托属性
    class A() {
        // 运算符重载
        operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
            return "$thisRef, thank you for delegating '${prop.name}' to me!"
        }

        operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) {
            println("$value has been assigned to ${prop.name} in $thisRef")
        }
    }



    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            val baseImpl = BaseImpl()

            BaseProxy(baseImpl).show()
            BaseProxy(baseImpl).otherShow()

            var name :String by A()
            name="aaaa"
            println(name)
        }
    }
}
