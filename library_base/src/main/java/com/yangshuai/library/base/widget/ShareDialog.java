package com.yangshuai.library.base.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yangshuai.library.base.BaseConst;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.utils.ToastUtil;
import com.yangshuai.library.base.utils.Utils;
import com.yangshuai.library.base.view.BaseListAdapter;
import com.yangshuai.library.base.view.NoScrollGridView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author hcp
 * @Created 2020-03-13
 * @Editor hcp
 * @Edited 2020-03-13
 * @Type
 * @Layout
 * @Api
 */
public class ShareDialog extends DialogFragment {
    public final static int SHARE_WECHAT = 0;     // 微信
    public final static int SHARE_WECHAT_COF = 1; // 朋友圈
    public final static int SHARE_POSTER = 2;     // 房源海报
    public final static int SHARE_QQ = 3;         // QQ好友
    public final static int SHARE_QQ_ZONE = 4;    // QQ空间
    public final static int SHARE_SINA = 5;       // 新浪微博
    public final static int SHARE_COPY = 6;       // 复制链接
    public final static int SHARE_SMS = 7;        // 短信
    public final static int SHARE_SAVE = 8;       // 保存图片
    public final static int SHARE_SPECIAL_POSTER = 9;       // 专题海报

    /**
     * 分享来源类型
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SHARE_PLATFORM.SHARE_WECHAT, SHARE_PLATFORM.SHARE_WECHAT_COF, SHARE_PLATFORM.SHARE_POSTER,
            SHARE_PLATFORM.SHARE_QQ, SHARE_PLATFORM.SHARE_QQ_ZONE, SHARE_PLATFORM.SHARE_SINA,
            SHARE_PLATFORM.SHARE_COPY, SHARE_PLATFORM.SHARE_SMS, SHARE_PLATFORM.SHARE_SAVE, SHARE_PLATFORM.SHARE_SPECIAL_SHARE})
    public @interface SHARE_PLATFORM {
        /**
         * 微信
         */
        int SHARE_WECHAT = 0;
        /**
         * 朋友圈
         */
        int SHARE_WECHAT_COF = 1;
        /**
         * 房源海报
         */
        int SHARE_POSTER = 2;
        /**
         * QQ好友
         */
        int SHARE_QQ = 3;
        /**
         * QQ空间
         */
        int SHARE_QQ_ZONE = 4;
        /**
         * 新浪微博
         */
        int SHARE_SINA = 5;
        /**
         * 复制链接
         */
        int SHARE_COPY = 6;
        /**
         * 短信
         */
        int SHARE_SMS = 7;
        /**
         * 保存图片
         */
        int SHARE_SAVE = 8;
        /**
         * 专题海报
         */
        int SHARE_SPECIAL_SHARE = 9;
    }

    private Window window;
    private View rootView;
    private onShareClickListener shareClickListener;
    private NoScrollGridView noScrollGridView;
    private List<ShareButtonData> mBaseButtonData;
    private List<ShareButtonData> mShowButtonData;
    private ShareButtonAdapter shareButtonAdapter;
    private int[] buttonImage = new int[]{
            R.mipmap.icon_share_wechat,
            R.mipmap.icon_share_wechat_cof,
            R.mipmap.icon_share_poster,
            R.mipmap.icon_share_qq,
            R.mipmap.icon_share_qq_zone,
            R.mipmap.icon_share_sina,
            R.mipmap.icon_share_copy,
            R.mipmap.icon_share_sms,
            R.mipmap.icon_share_save,
            R.mipmap.icon_share_special_poster,
    };
    private String[] buttonName = new String[]{
            "微信好友",
            "朋友圈",
            "房源海报",
            "QQ好友",
            "QQ空间",
            "新浪微博",
            "复制链接",
            "短信",
            "保存图片",
            "专题海报"
    };

    public ShareDialog() {
        mBaseButtonData = new ArrayList<>();
        mShowButtonData = new ArrayList<>();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        rootView = inflater.inflate(R.layout.base_share_dialog, container);
        initView();
        return rootView;
    }


    private void initView() {
        noScrollGridView = rootView.findViewById(R.id.share_view);
        if (mShowButtonData.size() == 1) {
            noScrollGridView.setNumColumns(1);
        } else if (mShowButtonData.size() == 2) {
            noScrollGridView.setNumColumns(2);
        } else if (mShowButtonData.size() == 3) {
            noScrollGridView.setNumColumns(3);
        } else {
            noScrollGridView.setNumColumns(4);
        }
        shareButtonAdapter = new ShareButtonAdapter(getContext(), mShowButtonData);
        noScrollGridView.setAdapter(shareButtonAdapter);

        TextView tvClose = rootView.findViewById(R.id.tv_share_close);
        tvClose.setOnClickListener(v -> dismiss());
    }

    private void initData() {
        for (int i = 0; i < buttonImage.length; i++) {
            ShareButtonData shareButtonData = new ShareButtonData();
            shareButtonData.setImage(buttonImage[i]);
            shareButtonData.setName(buttonName[i]);
            shareButtonData.setType(i);
            mBaseButtonData.add(shareButtonData);
        }
    }

    /**
     *
     */
    public void setVisible(int[] types) {
        for (int i = 0; i < mBaseButtonData.size(); i++) {
            for (int j = 0; j < types.length; j++) {
                if (mBaseButtonData.get(i).getType() == types[j]) {
                    mShowButtonData.add(mBaseButtonData.get(i));
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 下面这些设置必须在此方法(onStart())中才有效

        window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画(实现从底部弹出的对话框效果)
        window.setWindowAnimations(R.style.BaseBottomSheetDialog);

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }

    /**
     * 手动处理 DialogFragment Can not perform this action after onSaveInstanceState的异常
     *
     * @param manager
     * @param tag
     */
    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(this, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public interface onShareClickListener {

        /**
         * 分享菜单点击事件
         *
         * @param type 分享的类型  1微信 2朋友圈 3短信
         */
        void onShare(int type);
    }

    public void setShareClickListener(onShareClickListener shareClickListener) {
        this.shareClickListener = shareClickListener;
    }

    public class ShareButtonData {
        private String name;
        private int image;
        private int type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public class ShareButtonAdapter extends BaseListAdapter<ShareButtonData> {

        public ShareButtonAdapter(Context context, List<ShareButtonData> data) {
            super(context, data);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.base_cell_share_button;
        }

        @Override
        protected void bindHolder(int position, View convertView, ViewGroup parent, ShareButtonData item) {
            ImageView imageView = getHolder(convertView, R.id.img_logo);
            TextView textView = getHolder(convertView, R.id.txv_name);
            imageView.setBackgroundResource(item.getImage());
            textView.setText(item.getName());

            convertView.setOnClickListener(v -> {
                if (shareClickListener != null) {
                    //判断对应的软件是否安装
                    int type = item.getType();
                    String packageName = "";
                    String noticeStr = "";
                    if (type == 1 || type == 0) {
                        packageName = BaseConst.APP_WECHAT;
                        noticeStr = "检测到您未安装微信客户端，请前往商店下载安装";
                    } else if (type == 3 || type == 4) {
                        packageName = BaseConst.APP_QQ;
                        noticeStr = "检测到您未安装QQ客户端，请前往商店下载安装";
                    } else if (type == 5) {
                        packageName = BaseConst.APP_SINA;
                        noticeStr = "检测到您未安装新浪客户端，请前往商店下载安装";
                    }
                    if (!TextUtils.isEmpty(packageName) && getContext() != null) {
                        if (!Utils.isInstall(getContext(), packageName)) {
                            ToastUtil.Long(noticeStr);
                            return;
                        }
                    }
                    shareClickListener.onShare(item.getType());
                }
            });
        }
    }
}
