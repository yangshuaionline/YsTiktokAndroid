package com.yangshuai.library.base.entity;

import java.io.Serializable;

/**
 * @author hcp
 * @date 2020/4/2
 */
public class CollegeExamResultData implements Serializable {

    /**
     * result : false
     * msg : 没通过考试
     */

    private boolean result;
    private String msg;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
