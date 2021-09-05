package com.monk.kotlinstudy

class Inline {

    inline fun inlineRepeat(times: Int, action: (Int) -> Unit) {
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
            // https://zhuanlan.zhihu.com/p/343234358
            val inline=Inline()
            inline.inlineRepeat(2){}
            inline.noinlineRepeat(2){}


        }
    }
}