package com.monk.kotlinstudy

class Calculate {

    companion object{

        @JvmStatic
        fun main(args: Array<String>) {

            println(1 and 2) // a & b
            println(1 or 2) // a | b
            println(1 and 2)
            println(1 and 2)

            println("加法或连接字符串:${1.plus(2)}")
            println("减法:${1.minus(2)}")
            println("乘法:${1.times(2)}")
            println("除法和整除:${1.div(2)}")
            println("取余:${1.rem(2)}")

            val byte0:Int = 1
            val b1 = byte0 ushr 4 // 无符号右移4位
            println("无符号右移:$b1")
        }
    }
}