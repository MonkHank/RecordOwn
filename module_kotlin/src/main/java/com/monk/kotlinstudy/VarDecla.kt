package com.monk.kotlinstudy

import java.util.*

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



fun getScore(name: String) = when (name) {
    "Wonderful" -> 100
    "Tome" -> 86
    "Jack" -> 60
    else -> 0
}

fun doStudy(name:String?){
     println(name)
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
    println(str.toUpperCase(Locale.ROOT))
    println(str)

    val str2:String?=null
    try {
        str2!!.toString()
    } catch (e: Exception) {
        println("捕捉到了")
    }
}

/**
 * ?: 表示左边为null执行:后面的语句，否则不执行
 */
fun wenhaoMaohao() {
    val bean2 = Bean2(null)
    println("为null时 = ${bean2.name?:20}")

    val bean3 = Bean2("monk")
    println("不为null时 = ${bean3.name?:"30"}")

}

data class Bean2(val name: String?){
    data class Data(val name:String){
        var duration:Int?=0
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
    val list = listOf("a","b")
    val list2 = mutableListOf("a","b")
    list.forEach(::print)
    println()
    list2.forEach(::print)
    println()

    println("-------? 和 !!-----------")
    wenhaoAndDoubleGantanghao()

    println("------------------")
    operateMap()

    println("-------闭包-----------")
    val b = biBao()
    println(b)
    b()
    b()
    println(b())

    println("--------数组-----------")
    arraysFunction()

    println("--------?:-----------")
    wenhaoMaohao()

    println("-------String?------")
    doStudy(null)
}
