package com.monk.kotlinstudy

class Inline {

    inline fun repeat(times: Int, action: (Int) -> Unit) {
        for (index in 0 until times) {
            action(index)
        }
    }

    fun noinlineRepeat(times: Int, action: (Int) -> Unit) {
        for (index in 0 until times) {
            action(index)
        }
    }





    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            var a = 0
            repeat(100_000_000) {
                a += 1
            }


        }
    }
}