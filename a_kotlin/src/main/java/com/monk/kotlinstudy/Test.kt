package com.monk.kotlinstudy

/**
 * @author monk
 * @since 2023/10/26 18:09
 */
object Test {


}

fun main() {
    val str = "a_b_c_d"
    val newStr = str.replace("_",".")

    println(str)
    println(newStr)

    val javaClass = JavaClass()
    // 测试 isNotEmpty 对于 null 是否有效
    println(javaClass.nulls.isNotEmpty())

}