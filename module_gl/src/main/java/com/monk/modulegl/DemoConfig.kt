package com.monk.modulegl

import java.io.File

/**
 * @author monk
 * @since 2023/10/27 10:22
 */
object DemoConfig {
  // 人脸识别
  var BUNDLE_AI_FACE_PROCESSOR = "model" + File.separator + "ai_face_processor_video.bundle"

  //人脸分割相关
  var BUNDLE_AI_FACE_BEAUTY_VIDEO_PROCESSOR = "model" + File.separator + "ai_face_beauty_video_processor.bundle"
}