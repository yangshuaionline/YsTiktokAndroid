package com.yangshuai.library.base.widget.filter.model;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 分组多选适配器
 *
 * @author hcp
 * @created 2019-07-03
 */
public class MoreFilterSectionData extends SectionEntity<MoreFilterChildData> {

    public MoreFilterChildData childData;

    public MoreFilterSectionData(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MoreFilterSectionData(MoreFilterChildData t) {
        super(t);
        this.childData = t;
    }


    public MoreFilterChildData getChildData() {
        return childData;
    }

}
