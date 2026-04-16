package com.monk.kotlinstudy

import java.util.Random

/**
 * @author monk
 * @since 2023/10/26 18:09
 */

data class Person(val name:String, val grade:Int,val status:Int = 0)

fun main() {


    val list = arrayListOf<Person>()
    list.add(Person("nancy",50, 4))
    list.add(Person("jiessie",56, 0))
    list.add(Person("jack",45, 5))

    for (i in 0..3) {
        list.add(Person("name:$i", 20+i, Random().nextInt(4)))
    }
    list.add(Person("candy", 90, 0))

    println(list)


    list.sortBy { it.status!=0 }
    println(list)

    println("中文")

}
