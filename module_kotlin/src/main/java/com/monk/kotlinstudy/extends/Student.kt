package com.monk.kotlinstudy.extends

/*** 主构造函数*/
class Student(name: String, age: Int) : Person() {

    val height: Int = 175

    init {
        println("student init")
    }

    /*** 次构造函数*/
    constructor(name: String) : this("", 12) {}
    constructor() : this("", 12) {}


    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            val s = Student()

        }
    }

}