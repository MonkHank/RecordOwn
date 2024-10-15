package com.monk.kotlinstudy

data class User(val name: String ,
                val classInfo:String,
                val gradeInfo:String){

    var sp:Int=0

    var ocrDisclaimer: Int = 0
        get() = sp
        set(value) {
            println(value)
            println(field)
            val today = 111
            sp=today
        }


    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            val user = User("jack","class0","99")
            println(user)

            val user2 = user.copy(name = "monk")
            println(user2)

            user.ocrDisclaimer=112
            println(user.ocrDisclaimer)
        }
    }
}


