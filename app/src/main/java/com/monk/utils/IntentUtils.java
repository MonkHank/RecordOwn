package com.monk.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.monk.commonutils.ToastUtils;


/**
 * @author MonkHank
 * @date 2018-01-31.
 */
public final class IntentUtils {
    private static final String tag = "IntentUtils";
    public static final int PHOTO_REQUEST_GALLERY = 0x01;
    public static final int PHOTO_REQUEST_CAMERA = 0x02;
    public static final int PHOTO_REQUEST_CUT = 0x03;
    public static final int GO_TO_LOCAL_CONTACT = 1709211424;

    /**
     * 跳转至外部浏览器
     */
    public static void skipBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 跳转至外部打电话
     */
    public static void skipCallUp(Context context, String phoneNumber) {
        String perm = Manifest.permission.CALL_PHONE;
        if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
            // 没有权限，申请权限。
//            PermissionRequest request = new PermissionRequest(context, null);
//            request.requestPermission(Manifest.permission.CALL_PHONE);
            ToastUtils.showToast(context,"暂无权限");
        } else {
            // 有权限了，去放肆吧。
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
        }
    }

    /**
     * 打电话
     */
    public static void skipCallUp2(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 跳转至发送短信
     */
    public static void skipSendMessage(Context context, String phone, String message) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("smsto:" + phone));
        sendIntent.putExtra("sms_body", message);
        context.startActivity(sendIntent);
    }
//
//    /**
//     * 跳转至系统拍照 需要camera权限
//     */
//    public static File skipTakePhoto(Activity activity, Fragment v4Fragment, String photoName, boolean isCrop) {
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        File tempFile = null;
//        if (isCrop) {
//            // 指定uri，onActivityResult 中 data=null
//            tempFile = new File(Environment.getExternalStorageDirectory(), photoName);
//            Uri uri = Build.VERSION.SDK_INT > 23 ?
//                    FileProvider.getUriForFile(activity, Constant.FILE_PROVIDER_AUTHORITY, tempFile)
//                    : Uri.fromFile(tempFile);
//            //  uri: /storage/emulated/0/temp.png
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        }
//        LogUtil.v(tag, "tempFile:" + tempFile);
//        if (v4Fragment == null) {
//            activity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
//        } else {
//            v4Fragment.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
//        }
//        return tempFile;
//    }

    /**
     * 跳转至系统相册
     */
    public static void skipGallery(Activity activity, Fragment v4Fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (v4Fragment == null) {
            activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
        } else {
            v4Fragment.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
        }
    }

//    /**
//     * 跳转至系统裁剪
//     */
//    public static Uri skipCrop(Activity activity, Fragment v4Fragment, Uri uri) {
//        int[] widthAndHeight = DeviceUtils.getWidthAndHeight();
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setType("image");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", true);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
//
//        // 裁剪框的比例，1：1
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//
//        // 屏宽 x 屏高 像素  裁剪后输出图片的尺寸大小
//        intent.putExtra("outputX", 500);
//        intent.putExtra("outputY", 500);
//        LogUtil.v(tag, "outputX:" + widthAndHeight[0] + "\toutputY:" + widthAndHeight[1]);
//
//        // 图片格式
//        intent.putExtra("outputFormat", "PNG");
//        // 取消人脸识别
//        intent.putExtra("noFaceDetection", true);
//
////        intent.putExtra("return-data", true);
//        Uri uriTempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "temp.png");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriTempFile);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//
//        if (v4Fragment == null) {
//            activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
//        } else {
//            v4Fragment.startActivityForResult(intent, PHOTO_REQUEST_CUT);
//        }
//        return uriTempFile;
//    }

    /**
     * 跳转至系统通讯录
     * 由此可以说明vivo系列手机在通讯录这一块有点严格
     */
    public static void skipContact(Activity activity) {
        try {
            //等价于这个： content://com.android.contacts/contacts
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//            Uri uri = Uri.parse("content://contacts/people");
//            Intent intent = new Intent(Intent.ACTION_PICK, uri);
            activity.startActivityForResult(intent, GO_TO_LOCAL_CONTACT);
        } catch (Exception e) {
//            LogUtil.setLog(activity, e.toString());
            ToastUtils.showImageToast(activity, "请联系客服");
            activity.finish();
        }
    }

//    /**
//     * 安装对应apk
//     *
//     * @param file 安装文件
//     */
//    public static void skipInstallApk(Context context, File file) {
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        Uri uri = Build.VERSION.SDK_INT > Build.VERSION_CODES.M ?
//                FileProvider.getUriForFile(context, Constant.FILE_PROVIDER_AUTHORITY, file)
//                : Uri.fromFile(file);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
//
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//    }

//    /*** 通知设置页*/
//    public static void skipToNotificationSettings(Context context) {
//        Intent intent = new Intent();
//        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
//        intent.putExtra("app_package", getPackageName());
//        intent.putExtra("app_uid", context.getApplicationInfo().uid);
////            Uri uri = Uri.fromParts("package", getPackageName(), null);
////            intent.setData(uri);
//        context.startActivity(intent);
//    }

    /**
     * 跳转 应用程序列表界面【已安装的】
     *
     * @param context
     */
    public static void skipToAppListSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    // 针对此应用跳转至录屏界面
    private static final String[] Permissions_Video = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};

//    public static void skipToTakeVideo(final Activity activity, Fragment v4Fragment, final int requestCode) {
//        if (AndPermission.hasPermission(activity, Permissions_Video)) {
//            LogUtil.i(tag, Arrays.toString(Permissions_Video));
//            //如果不是先设置这个，那么new ALiYunUploadUtils(context,"video","",".mp4");会报错；
//            OssSTSUtils ossSTSUtils = new OssSTSUtils();
//            ossSTSUtils.getOssSTS(activity, 3, null);
//            if (v4Fragment == null) {
//                activity.startActivityForResult(new Intent(activity, CheckVideoActivity.class), requestCode);
//            } else {
//                v4Fragment.startActivityForResult(new Intent(activity, CheckVideoActivity.class), requestCode);
//            }
//            return;
//        }
//        PermissionRequest permissionRequest = new PermissionRequest(activity, new PermissionRequest.PermissionCallback() {
//            @Override
//            public void onSuccessful(List<String> permissions) {
//                //如果不是先设置这个，那么new ALiYunUploadUtils(context,"video","",".mp4");会报错；
//                OssSTSUtils ossSTSUtils = new OssSTSUtils();
//                ossSTSUtils.getOssSTS(activity, 3, null);
//                activity.startActivityForResult(new Intent(activity, CheckVideoActivity.class), requestCode);
//            }
//
//            @Override
//            public void onFailure(List<String> permissions) {
//                ToastUtils.showToast(activity, "暂无权限,无法录屏");
//            }
//        });
//        permissionRequest.requestPermission(Permissions_Video);
//    }

    public interface AudioListener {
        void prepareAudio();
    }

//    // 此应用准备录音的封装
//    private static final String Permissions_Record = Manifest.permission.RECORD_AUDIO;
//
//    public static void prepareAudio(final Activity activity, final AudioListener listener) {
//        /*// Vivo机必须要加这个判断，因为recorder
//        if(AndPermission.hasAlwaysDeniedPermission(activity,Arrays.asList(Permissions_Record))){
//            LogUtil.e(tag,Permissions_Record);
//            AndPermission.defaultSettingDialog(activity, 1805031059).show();//用默认的提示语。
//            return ;
//        }*/
//        if (AndPermission.hasPermission(activity, Permissions_Record)) {
//            LogUtil.i(tag, Permissions_Record);
//            listener.prepareAudio();
//            return;
//        }
//        PermissionRequest permissionRequest = new PermissionRequest(activity, new PermissionRequest.PermissionCallback() {
//            @Override
//            public void onSuccessful(List<String> permissions) {
//                listener.prepareAudio();
//            }
//
//            @Override
//            public void onFailure(List<String> permissions) {
//                ToastUtils.showToast(activity, "暂无权限,无法录音");
//            }
//        });
//        permissionRequest.requestPermission(Permissions_Record);
//    }

    public interface CaptureListener {
        void skipToCapture();
    }

    /**
     * 此应用跳转至扫码界面
     *
     * @param activity
     * @param listener
     */
//    public static void prepareCapture(final Activity activity, final CaptureListener listener) {
//        if (AndPermission.hasPermission(activity, Permissions_Video)) {
//            StringBuilder sb = new StringBuilder();
//            for (String s : Permissions_Video) {
//                sb.append(s + ",");
//            }
//            LogUtil.i(tag, sb.toString().substring(0, sb.length() - 1));
//            listener.skipToCapture();
//            return;
//        }
//        PermissionRequest permissionRequest = new PermissionRequest(activity, new PermissionRequest.PermissionCallback() {
//            @Override
//            public void onSuccessful(List<String> permissions) {
//                listener.skipToCapture();
//            }
//
//            @Override
//            public void onFailure(List<String> permissions) {
//                ToastUtils.showToast(activity, "暂无权限,无法扫码");
//            }
//        });
//        permissionRequest.requestPermission(Permissions_Video);
//    }

    /*public static void skipToOverlayPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(activity)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                activity.startActivityForResult(intent,GO_TO_OVERLAY_SETTINGS);
            }
        }
    }*/
}
