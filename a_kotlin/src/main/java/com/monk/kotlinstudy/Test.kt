package com.monk.kotlinstudy

/**
 * @author monk
 * @since 2023/10/26 18:09
 */
object Test {

  private var IDENTITY_MATRIX: FloatArray = FloatArray(16)

  init {
    println(IDENTITY_MATRIX)
  }

}

fun main() {
  println(Test.toString())
}