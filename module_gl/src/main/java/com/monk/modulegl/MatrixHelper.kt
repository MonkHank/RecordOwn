package com.monk.modulegl

object MatrixHelper {
  val CAMERA_TEXTURE_MATRIX = floatArrayOf(
    0.0f, -1.0f,
    0.0f, 0.0f,
    1.0f, 0.0f,
    0.0f, 0.0f,
    0.0f, 0.0f,
    1.0f, 0.0f,
    0.0f, 1.0f,
    0.0f, 1.0f
  )

  val CAMERA_TEXTURE_MATRIX_BACK = floatArrayOf(
    0.0f, -1.0f,
    0.0f, 0.0f,
    -1.0f, 0.0f,
    0.0f, 0.0f,
    0.0f, 0.0f,
    1.0f, 0.0f,
    1.0f, 1.0f,
    0.0f, 1.0f
  )

  val TEXTURE_MATRIX = floatArrayOf(
    1.0f, 0.0f,
    0.0f, 0.0f,
    0.0f, 1.0f,
    0.0f, 0.0f,
    0.0f, 0.0f,
    1.0f, 0.0f,
    0.0f, 0.0f,
    0.0f, 1.0f
  )

  //上下镜像
  val TEXTURE_MATRIX_CCRO_FLIPV_0 = floatArrayOf(
    1.0f, 0.0f,
    0.0f, 0.0f,
    0.0f, -1.0f,
    0.0f, 0.0f,
    0.0f, 0.0f,
    1.0f, 0.0f,
    0.0f, 0.0f,
    0.0f, 1.0f
  )
}