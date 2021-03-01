package com.monk.kotlinstudy

import java.util.*

/***
 * - var 声明变量
 * - val 声明常量
 * - 不同的数据类型用不同的容器保存
 * - kotlin会通过类型自动推断数据类型
 * - 我们也可以显式的指定数据类型
 */
fun main() {

    val range = 0..10 // 数学中的[0, 10]
    val range1 = 0 until 10 // 数学中的[0, 10)
    println(range)
    println(range1)

    println("------------------")
    forStatement()
    println()

    println("------------------")
    val list = listOf("a","b")
    val list2 = mutableListOf("a","b")
    list.forEach(::print)
    println()
    list2.forEach(::print)
    println()

    println("------------------")
    wenhaoAndDoubleGantanghao()

    println("------------------")
    operateMap()

    println("------------------")
    val b = biBao()
    println(b)
    b()
    b()
    println(b())

    println("-------------------")
    arraysFunction()
}

fun operateMap(){
    val list = arrayOf(1..5,50..55)
    val mergeList = list.flatMap { intRage->
        intRage.map { intElemet->
            "No.$intElemet"
        }
    }
    mergeList.forEach{print("$it,")}
    println()
}

fun biBao():()->(Int){
    var i=10
    return fun():Int{
        return i++
    }
}

fun forStatement() {
    //1. 遍历[0, 10]中的每一个元素
    for (i in 0..10) print("$i,")
    println()

    //2. 遍历[0, 10)的时候，每次循环会在区间范围内递增2，相当于 for-i 中的 i = i + 2 效果
    // step 关键字可以跳过其中一些元素
    for (i in 0 until 10 step 2)print("$i,")
    println()

    //3. 降序遍历[0, 10]中的每一个元素
    // downTo 关键字用来创建降序的空间
    for (i in 10 downTo 1)print("$i,")
    println()

    // 4. 遍历一个数组/列表，想同时取出下标和元素：
    val array = arrayOf("a", "b", "c")
    for ((index,e) in array.withIndex()) print("$index--$e,")
    println()

    //5. 遍历一个数组/列表，只取出下标:
    val array2 = arrayOf("a", "b", "c")
    for (index in array2.indices) print("$index,")//输出0，1，2
    println()

    // 6. 遍历取元素：
    val array3 = arrayOf("a", "b", "c")
    for (element in array3) print("$element,")//输出a,b,c

}

fun getScore(name: String) = when (name) {
    "Wonderful" -> 100
    "Tome" -> 86
    "Jack" -> 60
    else -> 0
}

fun doStudy(name:String?){
    if (name!=null) println(name)
}
fun doStudy2(name:String){
    println(name)
}

/**
 *  ? 相当于 if(=null)...
 *  !! 相当于 throw new NullPointerException
 */
fun wenhaoAndDoubleGantanghao(){
    val str : String?
    str="a"
    str.toUpperCase(Locale.ROOT)
    println(str)

    val str2:String?=null
    try {
        str2!!.toString()
    } catch (e: Exception) {
        println("捕捉到了")
    }
}

fun arraysFunction(){
    // int [] ints = new int[3];
    val ints = arrayOfNulls<Int>(3)
    ints[0]=0
    ints[1]=1
    ints[2]=2
    ints.forEach { int -> println(int)}
}
