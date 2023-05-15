package com.yangshuai.library.base.widget;

import android.os.Handler;
import android.os.Looper;

/**
 * @Author hcp
 * @Layout
 * @Api
 */
public abstract class MyCountUpTimer {

    private long mMillisInFuture;

    private long mCountUpInterval;

    private boolean mCancelled = false;

    public MyCountUpTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountUpInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countUp.
     */
    public synchronized final MyCountUpTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;


    private Handler mHandler = new Handler(Looper.getMainLooper(),(msg -> {
        int msgCount = msg.what;
        if (mCancelled) {
            return true;
        } else {
            mMillisInFuture += mCountUpInterval;
            onTick(mMillisInFuture);
            sendMessageDelayed();
        }
        return true;
    }));


    private void sendMessageDelayed() {
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG),mCountUpInterval);
    }

}
