package com.monk.kotlinstudy


class Inline{

    class Bean {
        var name:String?=null
        var gradeInfo:String?=null
        var classInfo:String?=null

        fun method0() {}
        fun method1() {}
        fun method2() {}

        override fun toString(): String {
            return "Bean(name=$name, gradeInfo=$gradeInfo, classInfo=$classInfo)"
        }
    }

    var bean:Bean?=null

    // 最常用的就是判空作用
    fun functionLet(){
        val result = "let".let {
            println(it.length)
            "let--"
        }
        println(result)

        // 内联扩展函数 ：let 函数针对一个可null对象的统一做null处理
        bean?.method0()
        bean?.method1()
        bean?.method2()

        bean = Bean()
        val let = bean?.let {
            it.method0()
            it.method1()
            it.method2()
            //最后一行返回值，按实际情况看是否需要返回值，其他内联函数也是一样
            "returnLet"
        }
        println("let:$let")
    }

    fun functionWith(){
        val user = User("jack","A","90")

        val with = with(user){
            bean?.name=name
            bean?.gradeInfo=gradeInfo
            bean?.classInfo=classInfo
            //最后一行返回值，按实际情况来
        }
        println("with:$with")
    }

    fun functionRun(){
        val user = User("monk", "B", "99")
        val run = user.run {
            bean?.name = name
            bean?.gradeInfo = gradeInfo
            bean?.classInfo = classInfo
            "returnRun"
        }
        println("run:$run")
    }

    // 跟run唯一的不同点是返回，apply返回的是传入对象的本身
    fun functionApply(){
        val user = User("monk", "B", "99")
        val apply = user.apply {
            bean?.name=name
            bean?.gradeInfo=gradeInfo
            bean?.classInfo=classInfo
        }
        println("apply:$apply")
    }

    // 跟let唯一的不同点是返回，also返回的是传入对象的本身
    fun functionAlso() {
        bean = Bean()
        val also = bean?.also {
            it.method0()
            it.method1()
            it.method2()
        }
        println("also:$also")
    }

    inline fun inlined(getString:()->String?)= println(getString())
    fun notInlined(getString:()->String?)= println(getString())


    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            val inline = Inline()
            inline.functionLet()
            inline.functionWith()
            inline.functionRun()
            inline.functionApply()
            inline.functionAlso()

            inline.inlined { "inlined" }
            inline.notInlined { "notInlined" }
        }
    }

}

