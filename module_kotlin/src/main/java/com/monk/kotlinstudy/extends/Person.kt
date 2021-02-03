package com.monk.kotlinstudy.extends

open class Person {

    private var name: String? = null
    private var age: Int?=0

    fun eat() {
        println("$name is eating. He is $age years old ")
    }


}

/*** 主构造函数*/
class Student(name: String, age: Int) : Person() {

    val height: Int = 175

    init {
        println("init")
    }

    /*** 次构造函数*/
    constructor(name: String) : this("", 12) {}
    constructor() : this("", 12) {}

}

class Student2 : Person{
    constructor(name:String,age: Int) : super(){}
}

data class Bean(val name: String, val innerBean: InnerBean){
    data class InnerBean(val name: String)
}

object SingleTon{
}
