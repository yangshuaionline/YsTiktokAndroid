package com.yangshuai.library.base.widget.edittext;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author hcp
 * on 2019-10-28
 */
public class ContentChangedEditText extends AppCompatEditText implements TextWatcher {

    private ContentTextWatcher mContentTextWatcher;


    public ContentChangedEditText(Context context) {
        super(context);
        addTextChangedListener(this);
    }

    public ContentChangedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(this);
    }

    public ContentChangedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addTextChangedListener(this);
    }


    public void addContentTextChangedListener(ContentTextWatcher contentTextWatcher) {
        mContentTextWatcher = contentTextWatcher;
        addTextChangedListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mContentTextWatcher != null)
            mContentTextWatcher.afterTextChanged(ContentChangedEditText.this,s.toString());
    }

    public interface ContentTextWatcher {
        void afterTextChanged(EditText editText, String content);
    }
}
