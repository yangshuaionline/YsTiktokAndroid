package com.yangshuai.library.base.exception;

/**
 * @Author hcp
 * @Created 4/22/19
 * @Editor hcp
 * @Edited 4/22/19
 * @Type
 * @Layout
 * @Api
 */
public class NoDataException extends Exception {


    @Override
    public String getMessage() {
        return "no data";
    }
}
