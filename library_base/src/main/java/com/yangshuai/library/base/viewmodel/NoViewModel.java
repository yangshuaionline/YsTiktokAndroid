package com.yangshuai.library.base.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

/**
 * 当页面没有viewModel的情况，可以用这个
 * @author hcp
 * @created 2019/3/14
 */
public class NoViewModel extends AndroidViewModel {

    public NoViewModel(@NonNull Application application) {
        super(application);
    }
}
