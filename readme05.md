# Surface 和 SurfaceView 

## Surface 
Surface对应了一块屏幕缓冲区，每个window对应一个Surface，任何View都要画在Surface的Canvas上。传统的view共享一块屏幕缓冲区，所有的绘制必须在UI线程中进行。
- [16ms看法](https://www.jianshu.com/p/a769a6028e51)
    - Frame Buffer/Back Buffer