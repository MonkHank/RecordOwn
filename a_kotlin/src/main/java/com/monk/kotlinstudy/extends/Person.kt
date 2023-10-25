package com.monk.kotlinstudy.extends

open class Person {

    private var name: String? = null
    private var age: Int?=0

    init {
        println("parent init")
    }

    fun eat() {
        println("$name is eating. He is $age years old ")
    }


}
