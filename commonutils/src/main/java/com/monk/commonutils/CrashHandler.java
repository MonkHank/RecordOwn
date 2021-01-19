package com.monk.commonutils;

import android.app.Application;
import android.os.Looper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author user
 */
public final class CrashHandler implements Thread.UncaughtExceptionHandler {
    private final String tag = "CrashHandler";

    private static Application mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private final String crash_text = "程序异常，即将退出";

    private CrashHandler() {
    }

    public static CrashHandler getInstance(Application context) {
        mContext = context;
        return Inner.INSTANCE;
    }

    private static class Inner {
        private static final CrashHandler INSTANCE = new CrashHandler();
    }

    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(ex);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDefaultHandler.uncaughtException(thread, ex);
    }

    private void handleException(Throwable ex) {
        if (ex == null) {
            return;
        }
        LogUtil.i(tag, Thread.currentThread().getName());
        ThreadManager.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.showToast(mContext, crash_text);
                Looper.loop();
            }
        });
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        String result = writer.toString();
        sb.append(result);
        try {
            LogUtil.e(sb.toString());
        } catch (Exception e) {
            LogUtil.i(tag, "an error occured while writing file...");
        }
        return sb.toString();
    }
}