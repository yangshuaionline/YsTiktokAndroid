package com.yangshuai.library.base.widget.edittext;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import com.yangshuai.library.base.utils.StringUtils;

/**
 * 1、只能输入一位数内容并替换当前对象 监听内容返回当前对象
 * 2、获取焦点时光标自动移动至内容末尾 聚焦选中当前背景
 * 3、监听删除键
 * @author hcp
 * on 2019-10-18
 */
public class ContentOneEditText extends androidx.appcompat.widget.AppCompatEditText implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {

    private int mSelectedResId;
    private int mUnSelectedResId;

    private String firstString;
    private boolean isChanged = false;

    private ContentTextWatcher mContentTextWatcher;


    public ContentOneEditText(Context context) {
        super(context);
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
    }

    public ContentOneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
    }

    public ContentOneEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
    }

    public void addContentTextChangedListener(ContentTextWatcher contentTextWatcher) {
        mContentTextWatcher = contentTextWatcher;
        addTextChangedListener(this);
    }


    /**
     * 设置背景
     * @param selectResId
     * @param unSelectResId
     */
    public void setBackgroundResId(int selectResId, int unSelectResId) {
        mSelectedResId = selectResId;
        mUnSelectedResId = unSelectResId;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            this.setBackgroundResource(mSelectedResId);
        } else {
            this.setBackgroundResource(mUnSelectedResId);
        }
    }

    /**
     * EditText光标始终保持在文字末尾
     * @param selStart
     * @param selEnd
     */
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart == selEnd) { //防止不能多选
            if (getText() == null) { //判空，防止出现空指针
                setSelection(0);
            } else setSelection(getText().length()); // 保证光标始终在最后面
        }
    }

    /**
     * 监听软键盘的删除按钮
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DEL && StringUtils.isEmpty(getText().toString())) {
            //this is for backspace
            afterTextChanged(Editable.Factory.getInstance().newEditable(""));
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        String editString2 = s.toString();
        if (editString2.length() == 2) {
            String schar;
            char[] chars = editString2.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                schar = String.valueOf(chars[i]);
                if (!firstString.equals(schar) && !isChanged) {
                    isChanged = true;
                    setText(schar);// 当前editText获取焦点时 改变当前控件值
                } else isChanged = false;
            }
            if (getText().toString().length() == 2) {
                setText(String.valueOf(chars[0]));
            }
        }
        firstString = getText().toString();
        if (mContentTextWatcher != null)
            mContentTextWatcher.afterTextChanged(ContentOneEditText.this,s.toString());
    }

    public interface ContentTextWatcher {
        void afterTextChanged(EditText editText, String content);
    }
}
