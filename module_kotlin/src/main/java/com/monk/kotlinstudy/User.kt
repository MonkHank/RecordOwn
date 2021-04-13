package com.monk.kotlinstudy

data class User(val name: String ,
                val classInfo:String,
                val gradeInfo:String){
    companion object{
        @JvmStatic
        fun main() {
            val user = User("jack","class0","99")
            println(user)

            val user2 = user.copy(name = "monk")
            println(user2)

        }
    }
}


