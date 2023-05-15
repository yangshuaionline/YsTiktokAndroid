package com.yangshuai.library.base;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @Author hcp
 * @Created 5/30/19
 * @Editor hcp
 * @Edited 5/30/19
 * @Type
 * @Layout
 * @Api
 */
public class SchameFilterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        ARouter.getInstance().build(uri).navigation();
        finish();
    }
}
