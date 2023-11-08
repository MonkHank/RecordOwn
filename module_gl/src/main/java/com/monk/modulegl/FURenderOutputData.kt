package com.monk.modulegl

/**
 * @author monk
 * @since 2023/10/26 17:44
 */
data class FURenderOutputData(var texture: FUTexture) {
  data class FUTexture(
    var textureId: Int,
    var width: Int,
    var height: Int
  )

}



