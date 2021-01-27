package com.monk.kotlinstudy

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
}