package com.yangshuai.module.login.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;

import com.yangshuai.lib.button.StateButton;
import com.yangshuai.library.base.utils.RxUtils;
import com.yangshuai.library.base.utils.Utils;
import com.yangshuai.module.login.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 倒计时按钮
 *
 * @Author hcp
 * @Created 4/20/19
 * @Editor hcp
 * @Edited 4/20/19
 * @Type
 * @Layout
 * @Api
 */
public class CountDownButton extends StateButton {

    private int count = 60;
    public static final String SECOND = "秒";
    private boolean countDown = true;

    private String originText;

    private Disposable disposable;

    public CountDownButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountDownButton, defStyleAttr, 0);
            countDown = a.getBoolean(R.styleable.CountDownButton_countDown, false);
            a.recycle();
        }

        setRadius(Utils.dp2Px(context, 3));
        setNormalTextColor(Color.WHITE);
        setPressedTextColor(Color.WHITE);
        setUnableTextColor(Color.WHITE);
        setNormalBackgroundColor(ContextCompat.getColor(context,R.color.theme));
        setPressedBackgroundColor(ContextCompat.getColor(context,R.color.theme_down));
        setUnableBackgroundColor(Color.parseColor("#CCCCCC"));
        setGravity(Gravity.CENTER);
    }

    /**
     * 设置倒计时时间
     *
     * @param count
     */
    public void setCountTimes(int count) {
        this.count = count;
    }

    /**
     * 是否允许倒计时
     *
     * @param enable
     */
    public void enableCountDown(boolean enable) {
        this.countDown = enable;
    }

    /**
     * 开始倒计时
     */
    public void startCountDown() {
        if (originText == null) {
            originText = getText().toString();
        }

        if (countDown) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                    .take(count + 1)
                    .map(aLong -> count - aLong)
                    .compose(RxUtils.schedulersTransformer())
                    .subscribe(new Observer<Long>() {

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            setText(originText);
                            setEnabled(true);
                        }

                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                            setEnabled(false);
                        }

                        @Override
                        public void onNext(Long aLong) {
                            setText(aLong + SECOND);
                        }
                    });
        }
    }

    /**
     * 取消倒计时
     */
    public void cancelCountDown() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            setText(originText);
            setEnabled(true);
        }
    }

    /**
     * 在空闲状态下才能激活按钮
     */
    public void setEnableOnfree(boolean enable) {
        if (disposable == null || disposable.isDisposed()) {
            setEnabled(enable);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
