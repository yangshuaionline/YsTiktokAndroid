package com.yangshuai.library.base.exception;

import java.io.IOException;

/**
 * @Author hcp
 * @Created 4/22/19
 * @Editor hcp
 * @Edited 4/22/19
 * @Type
 * @Layout
 * @Api
 */
public class NoNetworkException extends IOException {


    @Override
    public String getMessage() {
        return "no network";
    }
}
