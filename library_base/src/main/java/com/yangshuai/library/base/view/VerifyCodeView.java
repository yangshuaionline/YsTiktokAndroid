package com.yangshuai.library.base.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangshuai.library.base.R;
import com.yangshuai.library.base.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author hcp
 * @Created 10/21/19
 * @Editor hcp
 * @Edited 10/21/19
 * @Type
 * @Layout
 * @Api
 */
public class VerifyCodeView extends FrameLayout {

    private static final int INPUT_LENGTH = 6;
    private LinearLayout dispalyLayout;
    private EditText input;
    private List<TextView> tvViews = new ArrayList<>();
    private OnCompletedListener onCompletedListener;

    public VerifyCodeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        dispalyLayout = createDisplayLayout();
        input = createEditText();
        addView(dispalyLayout);
        addView(input);

        initLinstener();
    }

    private void initLinstener() {

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                displayCode(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void clearInput(){
        input.setText("");
    }

    public void setOnCompletedListener(OnCompletedListener onCompletedListener) {
        this.onCompletedListener = onCompletedListener;
    }

    private void displayCode(CharSequence s) {
        int textLength = s.length();
        for (int i = 0; i < INPUT_LENGTH; i++) {
            TextView tv = tvViews.get(i);
            if (i <= textLength - 1) {
                tv.setText(s.subSequence(i, i + 1));
            } else {
                tv.setText("");
            }
        }
        if (textLength == 6 && onCompletedListener != null) {
            onCompletedListener.onCompleted(s.toString());
        }
    }

    private LinearLayout createDisplayLayout() {
        LinearLayout layout = new LinearLayout(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dp2px(45));
        layout.setLayoutParams(layoutParams);

        for (int i = 0; i < INPUT_LENGTH; i++) {
            if (i != 0) {
                layout.addView(createSplitView());
            }
            TextView textView = createTextView();
            layout.addView(textView);
            tvViews.add(textView);
        }

        return layout;
    }

    private TextView createTextView() {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dp2px(40), dp2px(45));
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundResource(R.drawable.base_verifycode_bg);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.parseColor("#333333"));
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        return textView;
    }

    private View createSplitView() {
        View view = new View(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        view.setLayoutParams(layoutParams);
        return view;
    }

    private EditText createEditText() {
        EditText editText = new EditText(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dp2px(45));
        editText.setLayoutParams(layoutParams);

        editText.setCursorVisible(false);
        editText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new MaxLengthFilter(INPUT_LENGTH)});
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.transparent));
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        return editText;
    }

    private int dp2px(int dp) {
        return Utils.dp2Px(getContext(), dp);
    }

    class MaxLengthFilter implements InputFilter {

        int size;

        public MaxLengthFilter(int size) {
            this.size = size;
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.length() + dest.length() > size) {
                return "";
            }
            return null;
        }
    }

    public interface OnCompletedListener {
        void onCompleted(String text);
    }
}
