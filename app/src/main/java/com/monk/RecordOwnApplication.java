package com.monk;

import com.alibaba.android.arouter.launcher.ARouter;
import com.monk.aidldemo.BuildConfig;
import com.monk.basic.BaseApplication;

/**
 * @author monk
 * @date 2019-01-03
 */
public class RecordOwnApplication extends BaseApplication {
  private final String tag = "RecordOwnApplication";

  @Override
  public void onCreate() {
    super.onCreate();
    // TODO: 2023/10/24 积木路由
    // UIRouter.getInstance().registerUI("app");

    if (BuildConfig.DEBUG) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
      ARouter.openLog();     // 打印日志
      ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
    }
    ARouter.init(BaseApplication.Companion.getMApplication()); // 尽可能早，推荐在Application中初始化
  }
}
