package com.yangshuai.library.base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.piasy.biv.utils.IOUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @Author hcp
 * @Created 2019/3/21
 * @Editor hcp
 * @Edited 2019/3/21
 * @Type
 * @Layout
 * @Api
 */
public class Utils {


    /**
     * 富文本适配
     */
    public static String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:auto; height:auto;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static Float getDip2(Context ctx, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, ctx.getResources().getDisplayMetrics());
    }

    public static int dp2Px(Context context, int dp) {
        try {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (scale * dp + 0.5);
        } catch (Exception e) {
            // 跳转系统的设置
            KLog.w(e.toString());
            return 1;
        }
    }

    /**
     * 跳转权限设置页面(例如某个功能页申请权限失败后，可以弹框用户点击去设置对应的权限)
     *
     * @param context
     */
    public static void toAndroidSetting(Context context) {
        try {
            // 跳转APP自己的设置
            Intent mIntent = new Intent();
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 21) {
                mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else {
                mIntent.setAction(Intent.ACTION_VIEW);
                mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
                mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
            context.startActivity(mIntent);
        } catch (Exception e) {
            // 跳转系统的设置
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
            e.printStackTrace();
        }
    }

    /**
     * 跳转到拨号盘
     *
     * @param ctx
     * @param phone
     */
    public static void call(Context ctx, String phone) {
        if (TextUtils.isEmpty(phone)) {
            return;
        }
        try {
            Intent it = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 直接拨号(调用前需要申请权限)
     *
     * @param ctx
     * @param phone
     */
    public static void callPhoneWithDirect(Context ctx, String phone) {
        try {
            Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            ctx.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
            Utils.call(ctx, phone);
        }
    }

    /**
     * 获取联系人
     *
     * @param act
     * @param data
     * @return
     */
    @SuppressLint("Range")
    public static String[] getCustomer(Context act, Intent data) {
        String usernumber = "";
        String username = "";
        ContentResolver cr = act.getContentResolver();
        Uri contactData = data.getData();
        Cursor cursor = cr.query(contactData, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                username = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                                + contactId, null, null);
                while (phone.moveToNext()) {
                    usernumber = phone
                            .getString(phone
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    usernumber = usernumber.replace("-", "");
                    usernumber = usernumber.replace(" ", "");
                    usernumber = usernumber.replace("+", "");
                }
                phone.close();
            }
            cursor.close();
        }
        return new String[]{username, usernumber};
    }


    /**
     * 使用系统自带播放器播放视频
     *
     * @param type 1网络视频 2本地视频
     * @param url  视频地址或文件路径
     */
    public static void startPlayVideo(Context context, int type, String url) {
        // "http://oss-cn-shenzhen.aliyuncs.com/ujuzresources/65163FC0-C309-4437-989E-894254B48962.MOV"
        try {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            if (type == 1) {
                // 播放网络视频
                String extension = MimeTypeMap.getFileExtensionFromUrl(url);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                mediaIntent.setDataAndType(Uri.parse(url), mimeType);
                context.startActivity(mediaIntent);
            } else {
                // 播放本地视频
                File filePath = new File(url);
                Uri uri = null;
                if (SystemUtils.v24_N()) {
                    // 7.0+
                    uri = FileProvider.getUriForFile(context, "com.zwy.module", filePath);
                } else {
                    uri = Uri.parse("file://" + url);
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "video/*");
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.Short("无法播放该视频：" + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * MD5加密
     */
    public static String MD5(String sourceStr) {
        byte[] source = sourceStr.getBytes();
        String s = null;
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();
            char[] str = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 序列化对象
     *
     * @param file
     * @param obj
     * @return
     */
    public static boolean writeObj(File file, Serializable obj) {
        boolean isWrite = false;
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
            stream.writeObject(obj);
            stream.close();
            isWrite = true;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return isWrite;
    }

    /**
     * 反序列化对象
     *
     * @param file
     * @param <T>
     * @return
     */
    public static <T> T readObj(File file) {

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            return null;
        }
        T obj = null;
        try {
            ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
            try {
                obj = (T) stream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return obj;
    }

    /**
     * 安装APK
     *
     * @param file 文件路径
     */
    private void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            /* Android N 写法*/
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.zwy.module", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            /* Android N之前的老版本写法*/
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 检测网络
     *
     * @param context
     * @return
     */
    public static boolean checkNetWork(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());

    }

    /**
     * 检查定位服务是否开启
     *
     * @param
     * @return true 表示开启
     */
    public static boolean checkLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(),
                        Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * 调用系统发送短信
     *
     * @param context
     * @param phone   目标手机号
     * @param msg     短信内容
     */
    public static void sendSMS(Context context, String phone, String msg) {
        //"smsto:xxx" xxx是可以指定联系人的
        Uri smsToUri = Uri.parse("smsto:" + phone);

        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

        //"sms_body"必须一样，smsbody是发送短信内容content
        intent.putExtra("sms_body", msg);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Context context, View et) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制文本内容到粘贴板
     */
    public static void copyTextClipboard(Context context, String text) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    /**
     * 改变图标的颜色
     *
     * @param drawable 资源文件
     * @param colors   要改变的颜色值
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * 清空输入框数据
     */
    public static void clearAllEditText(View view) {
        if (view == null) {
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            final int len = viewGroup.getChildCount();
            for (int i = 0; i < len; i++) {
                View child = viewGroup.getChildAt(i);
                clearAllEditText(child);
            }
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            editText.setText("");
        } else {
            return;
        }
    }

    /**
     * 获取SD卡根目录(注意：要先确认已取得读写权限)
     *
     * @return
     */
    public static String sdPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isInstall(Context context, String packName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packName)) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * 判断是否为整数
     *
     * @param num 数字
     * @return
     */
    public static boolean isInteger(String num) {
        if (TextUtils.isEmpty(num))
            return false;
        Pattern p = Pattern.compile("[1-9]\\d*");
        Pattern p1 = Pattern.compile("\\+[1-9]\\d*");
        Matcher m = p.matcher(num);
        Matcher m1 = p1.matcher(num);
        if (m.matches() || m1.matches())
            return true;
        return false;

    }

    /**
     * contentResolver保存图片
     *
     */
    public static void savePicture(Context context, File file) {

        //Android10使用contentResolver方式保存图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ContentResolver contentResolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/yjzf/");
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, new Date().getTime());
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                OutputStream outputStream = contentResolver.openOutputStream(imageUri);
                FileInputStream fileInputStream = new FileInputStream(file);
                IOUtils.copy(fileInputStream, outputStream);
                fileInputStream.close();
                outputStream.close();

                //刷新
                MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()},
                        null, (path, uri) -> {
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            File imageDir = new File(sdPath() + "/Pictures" + "/hcp");
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
            File imageFile = new File(imageDir, new Date().getTime() + ".jpg");
            com.yangshuai.library.base.utils.IOUtils.copy(file, imageFile);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));

        }

    }

    /**
     * 下载图片
     * @param url
     */
    @SuppressLint("CheckResult")
    private void downloadImage(FragmentActivity activity, String url) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        Glide.with(AppContext.getAppContext())
                                .asFile()
                                .load(YJGlideUtil.getGlideUrl(url))
                                .into(new SimpleTarget<File>() {
                                    @Override
                                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                        saveFileToSDcard(resource);
                                    }
                                });
                    } else {
                        ToastUtil.Short("获取权限失败，请到设置中开启存储权限");
                    }
                }, error -> {
                    error.printStackTrace();
                    ToastUtil.Short("获取权限失败，请到设置中开启存储权限");
                });

    }

    /**
     * 保存图片文件到相册
     *
     * @param file
     */
    @SuppressLint("CheckResult")
    private void saveFileToSDcard(File file) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {

            Utils.savePicture(AppContext.getAppContext(),file);

            emitter.onNext(true);
            emitter.onComplete();
        })
                .compose(RxUtils.schedulersTransformer())
                .subscribe(aBoolean -> ToastUtil.Short("保存成功"), throwable -> ToastUtil.Short("保存失败"));
    }

}
