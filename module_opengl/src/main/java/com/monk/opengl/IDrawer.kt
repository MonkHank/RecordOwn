package com.monk.opengl

/**
 * @author monk
 * @since 2023/11/8 15:49
 */
interface IDrawer {
  fun draw()
  fun setTextureID(id: Int)
  fun release()
}
