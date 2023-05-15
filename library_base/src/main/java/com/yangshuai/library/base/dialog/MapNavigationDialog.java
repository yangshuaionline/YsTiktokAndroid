package com.yangshuai.library.base.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yangshuai.library.base.R;


/**
 * 地图跳转弹框菜单
 *
 * @author hcp
 * @created 2019-09-04
 */
public class MapNavigationDialog extends DialogFragment {

    private Window window;
    private View rootView;
    private JumpMapClickListener jumpMapClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        rootView = inflater.inflate(R.layout.base_map_dialog, container);
        initView();
        return rootView;
    }

    private void initView() {
        TextView tvClose = rootView.findViewById(R.id.cancel_btn);
        tvClose.setOnClickListener(v -> {
            dismiss();
        });

        View shareWechat = rootView.findViewById(R.id.tencent_btn);
        shareWechat.setOnClickListener(v -> {
            if (jumpMapClickListener != null) {
                jumpMapClickListener.onJumpMap("tencent");
            }
        });

        View shareWechatCof = rootView.findViewById(R.id.baidu_btn);
        shareWechatCof.setOnClickListener(v -> {
            if (jumpMapClickListener != null) {
                jumpMapClickListener.onJumpMap("baidu");
            }
        });

        View shareSms = rootView.findViewById(R.id.gaode_btn);
        shareSms.setOnClickListener(v -> {
            if (jumpMapClickListener != null) {
                jumpMapClickListener.onJumpMap("gaode");
            }
        });

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

    public interface JumpMapClickListener {

        /**
         * 地图跳转点击事件
         *
         * @param type 跳转的类型  gaode baidu tencent
         */
        void onJumpMap(String type);
    }

    public void setJumpMapClickListener(JumpMapClickListener jumpMapClickListener) {
        this.jumpMapClickListener = jumpMapClickListener;
    }
}
