package com.yangshuai.library.base.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangshuai.library.base.R;

/**
 * 判断删除对话框
 * @author hcp
 * @email: anhuifix@163.com
 */
public class DeleteCheckDialog extends BaseDialog {

    private TextView tv_center;
    private String showSome;
    public DeleteCheckDialog(@NonNull Context context, String showTitle) {
        super(context);
        this.showSome = showTitle;
    }

    public void setDialogClickListener(AlertDialogClickListener alertDialogClickListener) {
        setLeftButton("取消", v -> alertDialogClickListener.onCancel());
        setRightButton("确认", v -> alertDialogClickListener.onOk());
    }


    @Override
    protected View createContentView(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.base_dialog_delete_data, viewGroup, false);
        tv_center = view.findViewById(R.id.tv_center);
        return view;
    }


    public void setShowCenter(String title){
        tv_center.setText(title);
    }

    @Override
    public void onShow() {
    }

    @Override
    public void onDismiss() {
        tv_center.setText(" ");
    }


   public interface AlertDialogClickListener {
        void onCancel();

        void onOk();
    }
}
