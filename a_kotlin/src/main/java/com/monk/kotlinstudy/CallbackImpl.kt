package com.monk.kotlinstudy


class CallbackImpl{

    interface Callback{
        fun callback()
    }

    private var callback:Callback?=null //  java方式的回调
    /** 函数类型 */
    private var onConfirmListener: () -> Unit = {} // 无参无返回
    private var onCallback2:(Int,String)->Unit={ _: Int, _: String -> } // 有参无返回
    private var onCallback3:(text:String)->Unit={} // 有参无返回2
    private var onCallback4:(text:String,pos:Int)->Unit={_:String,_:Int->} // 有参无返回3，超过1个参数的需要显示
	
	private var interceptChecker: ()->Boolean = {false} // 无参有返回，默认返回false

    val sum = {x:Int,y:Int->x+y}



    fun method3(callback: Callback){}
    fun method4(cb: JavaClass.Callback){
        cb.callback(10)
    }
    fun methodCallback(){
        println("---------methodCallback-----------")
        println("java方式的回调 -- ${callback?.callback()}")
        onConfirmListener()
        onCallback2(20,"jack")
        onCallback3("onCallback3")

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

    fun setCallback3(listener: (text:String)->Unit):CallbackImpl{
        this.onCallback3 = listener
        return this
    }


    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            val callback = CallbackImpl()
            callback.setCallback { println("第1种方式的回调 -- 无参无返回") }
                    .setCallback2 { num, name -> println("第2种方式的回调 -- num:$num, name:$name") }
                    .setCallback3 { text -> println("第3种方式的回调 -- text:$text") }
                    .methodCallback()
        }
    }

}
