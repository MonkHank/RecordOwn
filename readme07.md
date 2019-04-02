# Window 和 Activity(View)之间的关系
>其实可以从 Activity 和 PhoneWindow 来分析两者之间的关系


DecorView 是 Activity 的窗口根视图，下面介绍 DecorView 的初始化以及和 ActionBar、contentView、viewRootImpl的关系。
## DecorView视图结构
![](/picture/decorview.webp)

每个Activity都对应一个窗口Window，这个窗口是PhoneWindow的实例，PhoneWindow对应的布局就是DecorView，是一个FrameLayout，DecorView内部又分为两部分，一部分就是ActionBar，另一部分就是ContentParent，即Activity在setContentView对应的布局。

## DecorView初始化
1. Activity 的 setContentView(int layoutResId)
```java
public void setContentView(@LayoutRes int layoutResId){
    getWindow().setContentView(layoutResId);
    initWindowDecorActionBar();
}
```

那么，getWindow()拿到的就是PhoneWindow对象，所以继续看PhoneWindow的setContentView(int layoutResId)方法
```java
public void setContentView(int layoutResID) {
    if (mContentParent == null) {
        installDecor();
    } else if (!hasFeature(FEATURE_CONTENT_TRANSITIONS)) {
        mContentParent.removeAllViews();
    }
    if (hasFeature(FEATURE_CONTENT_TRANSITIONS)) {
        final Scene newScene = Scene.getSceneForLayout(mContentParent, layoutResID,
                getContext());
        transitionTo(newScene);
    } else {
        mLayoutInflater.inflate(layoutResID, mContentParent);
    }
    mContentParent.requestApplyInsets();
    final Callback cb = getCallback();
    if (cb != null && !isDestroyed()) {
        cb.onContentChanged();
    }
    mContentParentExplicitlySet = true;
}
```
2. installDecor()
```java
private void installDecor() {
    mForceDecorInstall = false;
    if (mDecor == null) {
        mDecor = generateDecor(-1);
        mDecor.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        mDecor.setIsRootNamespace(true);
        if (!mInvalidatePanelMenuPosted && mInvalidatePanelMenuFeatures != 0) {
            mDecor.postOnAnimation(mInvalidatePanelMenuRunnable);
        }
    } else {
        mDecor.setWindow(this);
    }
    if (mContentParent == null) {
        mContentParent = generateLayout(mDecor);
        ...
    }
}
```
mDecor若为null，则调用generateDecor(-1)方法对mDecor进行初始化
```java
protected DecorView generateDecor(int featureId) {
    Context context;
    if (mUseDecorContext) {
        Context applicationContext = getContext().getApplicationContext();
        if (applicationContext == null) {
            context = getContext();
        } else {
            context = new DecorContext(applicationContext, getContext().getResources());
            if (mTheme != -1) {
                context.setTheme(mTheme);
            }
        }
    } else {
        context = getContext();
    }
    return new DecorView(context, featureId, this, getAttributes());
}
```
具体的 DecorView 与 PhoneWindow参看这部分；PhoneWindow 和 Activity 绑定参考 Activity 的启动流程，attach(16参数) 方法
- ActivityManagerService
     - Android 中也存在服务端和客户端的概念
     - 简称AMS，服务端对象，负责系统中所有Activity的生命周期
     - 会对 ActivityThread 进行初始化，任何一个 Activity 的启动都是由AMS和应用程序进程（主要是ActivityThread）相互配合来完成的
- ActivityThread
    - attach() 
        - ContextImpl 的初始化 
        - Application 的初始化
    - H
        - BIND_APPLICATION
            -  handleBindApplication(AppBindData)
            -  上面方法完成 Instrumentation 的初始化
        - LAUNCH_ACTIVITY  
            - handleLaunchActivity(ActivityClientRecord r，，) 
            - performLaunchActivity(ActivityClientRecord r，)
                - 初始化 Activity ，执行 Activity 的 attach(16参数) 方法 
- Activity 的 attach(16个参数)
    - 完成  PhoneWindow 的初始化
    - setContentView(layoutResId)
- PhoneWindow 的 setContentView(int layoutResID)
    - installDecor()    //安装DecorView
        - generateDecor(-1) // DecorView的初始化
        - generateLayout(mDecorView) // 生成 mContentParent 根容器
        - inflate(layoutResId,mContentParent) 
        
3. ContentParent
mContentParent 是一个 ViewGroup 对象，即 xml 文件所对应的 layout，LayoutInflater 最终会把 xml 文件解析并复制给 mContentParent

4. ActionBar
```java
// Activity setContentView(int) 第二个执行的函数
private void initWindowDecorActionBar() {
    Window window = getWindow();
    // Initializing the window decor can change window feature flags.
    // Make sure that we have the correct set before performing the test below.
    window.getDecorView();
    if (isChild() || !window.hasFeature(Window.FEATURE_ACTION_BAR) || mActionBar != null) {
        return;
    }
    mActionBar = new WindowDecorActionBar(this);
    mActionBar.setDefaultDisplayHomeAsUpEnabled(mEnableDefaultActionBarUp);
    mWindow.setDefaultIcon(mActivityInfo.getIconResource());
    mWindow.setDefaultLogo(mActivityInfo.getLogoResource());
}
```
Actionbar对应的布局文件是screen_action_bar.xml，在 WindowDecorActionBar(View) 的 init(View) 方法里就会找到其中的子控件或者布局完成初始化

## ActionBar 和 ContentParent 如何添加到 DecorView
准确来说，actionBar和contentParent并非是添加到decorView上去的，而是本身就存在于decorView，

- 对于有actionBar的activity，decorView的默认布局是screen_action_bar.xml，里面就会包含actionBar和contentParent
- 对于没有actionBar的activity，会根据activity所带的参数选择decorView的默认布局，例如screen_simple.xml

选择decorView的默认布局的相关的判断逻辑是installDecor方法中调用generateLayout完成的，以screen_action_bar.xml为例，可以看一下DecorView的默认布局
```xml
<com.android.internal.widget.ActionBarOverlayLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/decor_content_parent"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:splitMotionEvents="false"
    android:theme="?attr/actionBarTheme">
    <FrameLayout android:id="@android:id/content"
                 android:layout_width="match_parent"
                 android:layout_height="match_parent" />
    <com.android.internal.widget.ActionBarContainer
        android:id="@+id/action_bar_container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        style="?attr/actionBarStyle"
        android:transitionName="android:action_bar"
        android:touchscreenBlocksFocus="true"
        android:keyboardNavigationCluster="true"
        android:gravity="top">
        <com.android.internal.widget.ActionBarView
            android:id="@+id/action_bar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            style="?attr/actionBarStyle" />
        <com.android.internal.widget.ActionBarContextView
            android:id="@+id/action_context_bar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:visibility="gone"
            style="?attr/actionModeStyle" />
    </com.android.internal.widget.ActionBarContainer>
    <com.android.internal.widget.ActionBarContainer android:id="@+id/split_action_bar"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  style="?attr/actionBarSplitStyle"
                  android:visibility="gone"
                  android:touchscreenBlocksFocus="true"
                  android:keyboardNavigationCluster="true"
                  android:gravity="center"/>
</com.android.internal.widget.ActionBarOverlayLayout>
```
其中，id为action_bar_container和content分别对应actionBar和contentParent，setContentView()和initWindowDecorActionBar()会完成两者的初始化

## DecorView 与 ViewRootImpl 建立关系
PhoneWindow 通过 父类 Window 中的 setWindowManager(WindowManager wm, IBinder appToken, String appName); 方法将 Window（PhoneWindow）和 WindowManager（WindowManagerImpl）绑定

### 几个类的介绍
- **Window** 是个抽象类，它的实现，到目前来看只见到了 PhoneWindow，需要注意的是在Window中有个 **setWindowManager**  函数，此函数会将 Window（PhoneWIndow） 与WindowManagerImpl相关联.

- **WindowManager** 是接口，它的实现是 WindowManagerImpl，这个类通过 WindowManagerGlobal 来 完成添加、删除、更新view 的功能.

- **WindowManagerGlobal** 这个类主要是addView、removeView、updateViewLayout，操作，这个类也是和 ViewRootImpl 建立关系的一个类，通过ViewRootImpl 的 setView 函数进而执行 performTraversals 函数进行view的渲染等一系列操作.

- **ViewRootImpl** 的初始化是在WMG（WindowMangerGlobal）的addView方法中完成，ViewRootImpl 是Framework与native之间的通信桥梁，换句话说就是Framework与WMS之间是通过Binder机制通信，它的 setView 函数就是WMG调用的，很复杂的一个函数具体干了两件事：

1. requestLayout.    

2. 向wms发起显示当前window的请求；

最终还是会调用 performTraversals 这个2000多行代码的函数，这个函数主要做了以下操作：

1. 获取 Surface对象，用于图形绘制   

2. 执行performMeasure函数    

3. 执行performLayout函数    

4. 执行performDraw函数

其中第4步我们会发现，会执行draw方法，进而执行ThreadRenderer中的draw函数（AndroidSDK 4.0 (14) 之后默认都是开启硬件加速，如果强制关闭硬件加速，那么会执行ViewRootImpl内部的软件加速绘制方法drawSoftware函数），这个与自定义view时候的draw函数是一致的，其实归根结底就是通过ViewRootImpl来实现view的渲染过程.

源码是Android 2.3版本的。到了Android 4.0之后，ViewRoot类的名字改成了ViewRootImpl，它们的作用仍然一样的.

- **ThreadRenderer**
代理Reader Thread的一个类，也就是Open GL线程，硬件加速线程，4.0之后Android默认开启硬件加速

