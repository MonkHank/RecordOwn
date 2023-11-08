package com.monk.modulegl

/**
 * @author monk
 * @since 2023/10/26 17:43
 */
data class FURenderFrameData(var texMatrix:FloatArray, var mvpMatrix: FloatArray) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as FURenderFrameData

    if (!texMatrix.contentEquals(other.texMatrix)) return false
    if (!mvpMatrix.contentEquals(other.mvpMatrix)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = texMatrix.contentHashCode()
    result = 31 * result + mvpMatrix.contentHashCode()
    return result
  }
}
