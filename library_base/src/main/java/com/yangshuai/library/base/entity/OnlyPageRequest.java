package com.yangshuai.library.base.entity;

/**
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2020/5/14 10:24
 * @UpdateUser: 列表请求页
 * @UpdateDate: 2020/5/14 10:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OnlyPageRequest {

    private int pageNo;
    private int pageSize;

    public OnlyPageRequest(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
