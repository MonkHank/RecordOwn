2023-11-10 16:47:05 星期五

## 流程
1. 接收一个file（视频）
2. 通过 MediaExtractor 解析视频，得到 MediaFormat 去创建 MediaCodec
   - 可以通过 MediaExtractor 操作视频文件(seek,read样本数据,前进帧,获取时间...)
3. 根据 MediaFormat 的 mine 类型创建相应的 MediaCodec 视频解码器
4. MediaCodec 配置(configure) Surface
5. 将 Codec 的 InputBuffer 传给 MediaExtractor， 进行读取，并进入下一帧继续
   - 这一步已经将 MediaExtractor 解析的视频数据传给了 Codec，Codec 会将数据关联给其Surface
   - 使用opengl和surfacetexture将surface上的视频绘制出来
6. 将 Codec 的 OutputBuffer 获取，可以将数据传递出去等等操作