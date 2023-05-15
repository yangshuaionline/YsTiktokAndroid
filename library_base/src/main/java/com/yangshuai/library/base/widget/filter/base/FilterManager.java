package com.yangshuai.library.base.widget.filter.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选管理器
 */
public class FilterManager {

    public List<FilterLink> links;
    private long lastTimeMillis;
    // 禁止点击筛选按钮
    private boolean enableClick;


    public static FilterManager newInstance() {
        return new FilterManager();
    }

    public FilterManager() {
        links = new ArrayList<>();
    }

    public FilterManager addLink(FilterLink link) {
        links.add(link);
        return this;
    }

    public void setEnableClick(boolean enableClick) {
        this.enableClick = enableClick;
    }

    public FilterManager run() {
        for (FilterLink _link : links) {
            final FilterLink link = _link;
            link.getButtonView().setOnClickListener(view -> {
                if (enableClick) {
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                //延迟点击
                if (lastTimeMillis > 0 && currentTimeMillis - lastTimeMillis < 600) {
                    return;
                }

                link.getButtonView().setChecked(!link.getButtonView().isChecked());
                lastTimeMillis = currentTimeMillis;
            });
            link.getButtonView().setOnFilterButtonListener(((fbv, isChecked) -> {
                if (isChecked) {
                    link.getContainerView().show();
                    for (FilterLink filterLink : links) {
                        //关闭掉那些还在打开的过滤按钮
                        if (filterLink != link && filterLink.getButtonView().isChecked()) {
                            filterLink.getButtonView().setChecked(false);
                            filterLink.getContainerView().close(false);
                        }
                    }
                } else {
                    link.getContainerView().close();
                }
            }));
            link.getContainerView().setOnFilterContainerListener(new BaseFilterContainerView.OnFilterContainerListener() {
                @Override
                public void onShow(BaseFilterContainerView fcv) {

                }

                @Override
                public void onClose(BaseFilterContainerView fcv) {
                    link.getButtonView().setChecked(false);
                }
            });
        }
        return this;
    }


    public boolean isShowing() {
        for (FilterLink link : links) {
            if (link.getContainerView().isShowing()) {
                return true;
            }
        }
        return false;
    }


    public void close() {
        for (FilterLink link : links) {
            if (link.getContainerView().isShowing()) {
                link.getContainerView().close();
            }
        }
    }


    public static class FilterLink {
        private BaseFilterButtonView buttonView;
        private BaseFilterContainerView containerView;
        private boolean forceClose;

        public FilterLink(BaseFilterButtonView buttonView, BaseFilterContainerView containerView) {
            this.buttonView = buttonView;
            this.containerView = containerView;
            //buttonView.setExtend(this);
            //containerView.setExtend(this);
        }

        public BaseFilterButtonView getButtonView() {
            return buttonView;
        }

        public boolean isForceClose() {
            return forceClose;
        }

        public void setForceClose(boolean forceClose) {
            this.forceClose = forceClose;
        }

        public BaseFilterContainerView getContainerView() {
            return containerView;
        }
    }
}
