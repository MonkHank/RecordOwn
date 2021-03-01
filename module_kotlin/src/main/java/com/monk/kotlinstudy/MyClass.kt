package com.monk.kotlinstudy

import org.intellij.lang.annotations.PrintFormat


fun main(args: Array<String>) {

    println("-----------------------")
    val user = User("jack","class0","99")
    println(user)

    val user2 = user.copy(name = "monk")
    println(user2)

    println("----------------------")
    val callback = CallbackImpl()
    callback.functionLet()
    callback.functionWith()
    callback.functionRun()
    callback.functionApply()
    callback.functionAlso()

    println("---------------------")
    callback.setCallback { println("num:$10") }
            .setCallback2 { num, name -> println("num:$num--name:$name") }
            .methodCallback()

}


/*** 数据类*/
data class User(val name: String, val classInfo:String,val gradeInfo:String)

/*** 属性默认都有get()方法*/
class Getter {
    val variable: String
        get() = "jack"
}

class CallbackImpl{
    var bean:Bean?=null

    private var callback:Callback?=null

    private var onConfirmListener: () -> Unit = {}
    private var onCallback2:(num:Int,name:String)->Unit={ _: Int, _: String -> }

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

    fun functionAnonymity() {

        // 只有一个参数无返回的直接这样写
        bean?.method4 {
        }
    }

    fun methodCallback(){
        println("callback:"+callback?.callback())
        onConfirmListener()
        onCallback2(20,"jack")
        onConfirmListener{
        }
    }

    private fun onConfirmListener(function: () -> Unit) {
        println("onConfirmListener")
    }

    fun setCallback(listener: () -> Unit): CallbackImpl {
        this.onConfirmListener = listener
        return this
    }
    fun setCallback2(listener: (num:Int,name:String) -> Unit):CallbackImpl{
        this.onCallback2=listener
        return this
    }

}
interface Callback{
    fun callback()
}

interface Callback2{
    fun callback2(num:Int,name:String)
}

class Bean {
    var name:String?=null
    var gradeInfo:String?=null
    var classInfo:String?=null

    fun method0() {
    }

    fun method1() {
    }

    fun method2() {
    }

    fun method3(callback: Callback){
    }
    fun method4(cb: JavaClass.Callback){
        cb.callback(10)
    }

    override fun toString(): String {
        return "Bean(name=$name, gradeInfo=$gradeInfo, classInfo=$classInfo)"
    }


}