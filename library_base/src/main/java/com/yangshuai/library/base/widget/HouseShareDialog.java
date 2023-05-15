package com.yangshuai.library.base.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yangshuai.library.base.R;

/**
 * 房源分享弹框菜单
 *
 * @author hcp
 * @created 2019-09-04
 */
public class HouseShareDialog extends DialogFragment {

    private Window window;
    private View rootView;
    private onShareClickListener shareClickListener;

    private boolean weichat = true, wmonment = true, shortmsg = true, poster = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        rootView = inflater.inflate(R.layout.base_house_share_dialog, container);
        initView();
        return rootView;
    }

    private void initView() {
        TextView tvClose = rootView.findViewById(R.id.tv_share_close);
        tvClose.setOnClickListener(v -> {
            dismiss();
        });

        View shareWechat = rootView.findViewById(R.id.ll_share_wechat);
        shareWechat.setOnClickListener(v -> {
            if (shareClickListener != null) {
                shareClickListener.onShare(1);
            }
        });

        View shareWechatCof = rootView.findViewById(R.id.ll_share_wechat_cof);
        shareWechatCof.setOnClickListener(v -> {
            if (shareClickListener != null) {
                shareClickListener.onShare(2);
            }
        });

        View shareSms = rootView.findViewById(R.id.ll_share_sms);
        shareSms.setOnClickListener(v -> {
            if (shareClickListener != null) {
                shareClickListener.onShare(3);
            }
        });

        View sharePoster = rootView.findViewById(R.id.ll_share_poster);
        sharePoster.setOnClickListener(v -> {
            if (shareClickListener != null) {
                shareClickListener.onShare(4);
            }
        });

        shareWechat.setVisibility(weichat ? View.VISIBLE : View.GONE);
        shareWechatCof.setVisibility(wmonment ? View.VISIBLE : View.GONE);
        shareSms.setVisibility(shortmsg ? View.VISIBLE : View.GONE);
        sharePoster.setVisibility(poster ? View.VISIBLE : View.GONE);
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


    public void setVisible(boolean weichat, boolean wmonment, boolean shortmsg, boolean poster) {
        this.weichat = weichat;
        this.wmonment = wmonment;
        this.shortmsg = shortmsg;
        this.poster = poster;
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

    /**
     * 手动处理 DialogFragment Can not perform this action after onSaveInstanceState的异常
     * @param manager
     * @param tag
     */
    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
//        super.show(manager, tag);
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(this, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
