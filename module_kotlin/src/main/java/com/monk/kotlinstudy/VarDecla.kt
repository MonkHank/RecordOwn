package com.monk.kotlinstudy

import kotlin.concurrent.thread

/***
 * - var 声明变量
 * - val 声明常量
 * - 不同的数据类型用不同的容器保存
 * - kotlin会通过类型自动推断数据类型
 * - 我们也可以显式的指定数据类型
 */
fun main() {
    var name = "张三"

    var i = 18
    var ii: Int = 19// 指定类型

    val PI = 3.1415926 // 只读类型，不允许修改

    val b: Int = 0b0011 // 用0b来表示二进制
    println("b的十进制：$b")

    val s = "abc"
    println("$s.length is ${s.length}")

    val range = 0..10 // 数学中的[0, 10]
    val range1 = 0 until 10 // 数学中的[0, 10)
    println(range)
    println(range1)

    forStatement()

    val list = listOf("a","b")
    val list2 = mutableListOf("a","b")

    Thread { TODO("Not yet implemented") }
    Thread(object :Runnable{
        override fun run() {
            TODO("Not yet implemented")
        }
    })

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