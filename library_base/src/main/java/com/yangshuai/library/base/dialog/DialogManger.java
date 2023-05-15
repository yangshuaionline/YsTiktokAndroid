package com.yangshuai.library.base.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

import com.yangshuai.library.base.entity.DialogBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2020/3/26 15:39
 * @UpdateUser: hcp
 * @UpdateDate: 2020/3/26 15:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DialogManger {

    /**
     * 强制更新，强制修改密码，非强制更新，入职邀请，实名认证弹窗，考试不通过，强制提醒公告，新房报备
     * <p>
     * priority：10 强制更新
     * 9 强制修改密码
     * 8 入职邀请
     * 7 实名认证弹窗
     * 6 考试不通过
     * 5 强制提醒公告
     * 4 新房报备
     */

    public static final int STRONGUPDATA = 10;//强制更新
    public static final int STRONGCHANGEPW = 9;//强制修改密码
    public static final int ENTRY = 8;//入职邀请
    public static final int REALPERSON = 7;//实名认证弹窗
    /**
     * 考试不通过
     */
    public static final int EXAM_FAILED = 6;

    private List<DialogBean> dialogList = new ArrayList<>();//还未显示的dialog的list，这里是无序的
    private List<DialogBean> currentDialogList = new ArrayList<>();//当前显示的dialog的list，最后一个就是显示在最上面的，是按优先级正序的。这里需要理解下，因为只有比上一个dialog优先级高的时候，才会显示并加入到这个list

    private DialogManger() {

    }

    public static DialogManger getInstance() {
        return holderManger.dialogManger;
    }

    private static class holderManger {
        private static final DialogManger dialogManger = new DialogManger();
    }

    /**
     * 添加dialog，默认优先级最低
     *
     * @param dialog
     */
    public void addDialog(Dialog dialog) {
        if (dialog != null) {
            addDialog(dialog, 0);
        }
    }

    /**
     * 添加dialog
     *
     * @param dialog
     * @param priority 优先级
     */
    public void addDialog(Dialog dialog, int priority) {
        if (dialog != null) {
            addDialog(dialog, priority, false);
        }
    }

    /**
     * 添加dialog，并判断是否清空其他弹窗
     *
     * @param dialog
     * @param priority 优先级
     * @param flag     是否清除之前的弹窗
     */
    public void addDialog(Dialog dialog, int priority, boolean flag) {
        if (dialog != null) {
            DialogBean dialogBean = new DialogBean(dialog, priority);
            dialogBean.setDialogView(dialog.getWindow().getDecorView());
            if (flag) {
                dialogList.clear();
                if (currentDialogList.size() > 0) {
                    for (DialogBean current : currentDialogList) {
                        current.getDialog().setOnDismissListener(null);
                        current.getDialog().dismiss();
                    }
                }
                currentDialogList.clear();
            }
            try {
                show(dialogBean);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void show(DialogBean newDialog) {
        //都需要设置监听
        newDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                currentDialogList.remove(newDialog);//关闭后从显示列表上删除
                nextDialog();
            }
        });

        //先计算出要弹出的弹窗
        if (currentDialogList.size() == 0) {//没有弹窗显示时
            //直接显示新的dialog
            newDialog.getDialog().show();
            currentDialogList.add(newDialog);//加入到已显示的列表
        } else {//有弹窗显示时
            if (newDialog.getPriority() > currentDialogList.get(currentDialogList.size() - 1).getPriority()) {//优先级高于当前显示的dialog
                //显示新的dialog
                newDialog.getDialog().show();
                //之前显示的弹窗暂时隐藏掉
                currentDialogList.get(currentDialogList.size() - 1).getDialogView().setVisibility(View.INVISIBLE);
                currentDialogList.add(newDialog);//加入到已显示的列表
            } else {//优先级和当前的dialog相等或者低于当前
                dialogList.add(newDialog);//加入到待显示列表
            }
        }
    }

    /**
     * 计算得出下一个要展示的dialog
     */
    private void nextDialog() {
        //未显示列表里无dialog时
        if (dialogList.size() == 0) {
            //显示列表不为空时
            if (currentDialogList.size() > 0) {
                //继续展示之前已经展示出来的
                currentDialogList.get(currentDialogList.size() - 1).getDialogView().setVisibility(View.VISIBLE);
            } else {
                //显示列表为空时
                //不做处理
            }
        }
        //未显示列表里有dialog时
        else {
            //显示列表不为空时
            if (currentDialogList.size() > 0) {
                //1、先拿取已显示list里优先级最高的
                DialogBean currentDialogBean = currentDialogList.get(currentDialogList.size() - 1);//最后一个就是优先级最高的
                //2、再拿取未显示list里优先级最高的
                DialogBean notShowDialogBean = dialogList.get(0);
                for (int i = 0; i < dialogList.size(); i++) {
                    if (notShowDialogBean.getPriority() < dialogList.get(i).getPriority()) {
                        notShowDialogBean = dialogList.get(i);
                    }
                }
                //3、对比出优先级最高的，如果优先级一样。那么依旧显示之前显示的
                //4、显示优先级较高的
                if (currentDialogBean.getPriority() < notShowDialogBean.getPriority()) {//未展示的优先级较高
                    notShowDialogBean.getDialog().show();//展示未展示的
                    currentDialogList.add(notShowDialogBean);//加入到展示列表里
                    dialogList.remove(notShowDialogBean);//从未显示列表里删除
                } else {
                    //继续展示之前已经展示出来的
                    currentDialogList.get(currentDialogList.size() - 1).getDialogView().setVisibility(View.VISIBLE);
                }
            } else {
                //显示列表为空时
                //1、再拿取未显示list里优先级最高的
                DialogBean notShowDialogBean = dialogList.get(0);
                for (int i = 0; i < dialogList.size(); i++) {
                    if (notShowDialogBean.getPriority() < dialogList.get(i).getPriority()) {
                        notShowDialogBean = dialogList.get(i);
                    }
                }

                //2、显示优先级较高的
                notShowDialogBean.getDialog().show();
                currentDialogList.add(notShowDialogBean);//加入到展示列表里
                dialogList.remove(notShowDialogBean);//从未显示列表里删除
            }
        }
    }

}
