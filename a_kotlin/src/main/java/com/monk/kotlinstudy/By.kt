package com.monk.kotlinstudy

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

var observableProp: String by Delegates.observable("默认值：xxx"){
        property, oldValue, newValue ->
    println("property: $property: $oldValue -> $newValue ")
}
// 测试
fun main() {
    observableProp = "第一次修改值"
    observableProp = "第二次修改值"

    val baseImpl = By.BaseImpl()

    By.BaseProxy(baseImpl).show()
    By.BaseProxy(baseImpl).otherShow()

    var name :String by By.A()
    name="aaaa"
    println(name)

    val user=By.User()
    user.name="first"
    user.name="second"

}



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

    class User{
        var name:String by Delegates.observable("test"){property, oldValue, newValue ->
            println("property:$property")
            println("oldValue:$oldValue -- newValue:$newValue")
        }
    }

}
