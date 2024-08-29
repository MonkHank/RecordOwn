package com.monk.kotlinstudy

class For {

    fun forStatement() {
        //1. 遍历[0, 10]中的每一个元素
        for (i in 0..10) print("$i,")
        println()

        //2. 遍历[0, 10)的时候，每次循环会在区间范围内递增2，相当于 for-i 中的 i = i + 2 效果
        // step 关键字可以跳过其中一些元素
        for (i in 0 until 10 step 2) print("$i,")
        println()

        //3. 降序遍历[0, 10]中的每一个元素
        // downTo 关键字用来创建降序的空间
        for (i in 10 downTo 1) print("$i,")
        println()

        // 4. 遍历一个数组/列表，想同时取出下标和元素：
        val array = arrayOf("a", "b", "c")
        for ((index, e) in array.withIndex()) print("$index--$e,")
        println()

        //5. 遍历一个数组/列表，只取出下标:
        val array2 = arrayOf("a", "b", "c")
        for (index in array2.indices) print("$index,")//输出0，1，2
        println()

        // 6. 遍历取元素：
        val array3 = arrayOf("a", "b", "c")
        for (element in array3) print("$element,")//输出a,b,c
        println()

        // 7. break
        println("----7.break------")
        val array4 = arrayOf(1, 2, 3, 4, 5, 6)
        run breaker@{
            array4.forEach {
                println("it = $it")
                if (it == 4) return
            }
            println("执行了....") // 内部这里是不执行的
        }
        println("后续执行了....") // 这里是执行的

        // 8. break
        println("----8.break------")
        run breaker@{
            for (i in array4.indices) {
                println("i = $i")
                if (i == 2) return@breaker
            }
            println("执行了....") // 内部这里是不执行的
        }

        println()
        for (i in array4.indices){
            print(i)
            if (i==2)break
        }
        println()


        //9. continue
        println("-----9.continue-----")
        array4.forEach {
            if (it == 2) return@forEach
            print(it)
        }

        println()
        for (i in array4.indices){
            if (i==2)continue
            print(i)
        }
        println()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val fo = For()
            fo.forStatement()

        }
    }
}

