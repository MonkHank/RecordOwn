package com.monk.kotlinstudy


class CallbackImpl{

    interface Callback{
        fun callback()
    }

    private var callback:Callback?=null

    /** 函数类型 */
    private var onConfirmListener: () -> Unit = {} // 无参无返回
    private var onCallback2:(Int,String)->Unit={ _: Int, _: String -> } // 有参无返回

    val sum = {x:Int,y:Int->x+y}



    fun method3(callback: Callback){}
    fun method4(cb: JavaClass.Callback){
        cb.callback(10)
    }
    fun methodCallback(){
        println("---------methodCallback-----------")
        println("callback:"+callback?.callback())
        onConfirmListener()
        onCallback2(20,"jack")
        onConfirmListener{}
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



    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            val callback = CallbackImpl()
            callback.setCallback { println("num:$10") }
                    .setCallback2 { num, name -> println("num:$num--name:$name") }
                    .methodCallback()
        }
    }

}
