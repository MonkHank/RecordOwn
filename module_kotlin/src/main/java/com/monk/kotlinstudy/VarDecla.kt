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

    forStatement()

    val list = listOf("a","b")
    val list2 = mutableListOf("a","b")

    wenhaoAndDoubleGantanghao()

}

fun forStatement() {
    // 遍历[0, 10]中的每一个元素
    for (i in 0..10) print(i)
    println()

    // 遍历[0, 10)的时候，每次循环会在区间范围内递增2，相当于 for-i 中的 i = i + 2 效果
    // step 关键字可以跳过其中一些元素
    for (i in 0 until 10 step 2)print(i)
    println()

    // 降序遍历[0, 10]中的每一个元素
    // downTo 关键字用来创建降序的空间
    for (i in 10 downTo 1)print(i)
    println()
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
    var str : String?
    str="a"
    str?.toUpperCase()
    println(str)

    var str2:String?=null
    try {
        str2!!.toString()
    } catch (e: Exception) {
        println("捕捉到了")
    }
}