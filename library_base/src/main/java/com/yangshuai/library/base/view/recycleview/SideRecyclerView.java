package com.yangshuai.library.base.view.recycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

/**
 * 侧滑RecyclerView
 * */
public class SideRecyclerView extends SwipeMenuRecyclerView {

    public SideRecyclerView(Context context) {
        super(context);
    }

    public SideRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        boolean flag = false;
        int action = e.getAction();
        int x = (int) e.getX();
        int y = (int) e.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                int touchingPosition = getChildAdapterPosition(findChildViewUnder(x, y));
                if (touchingPosition != mOldTouchedPosition && mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
                    flag = true;
                }
                break;
            }
            // They are sensitive to retain sliding and inertia.
            case MotionEvent.ACTION_MOVE: {
            }
            case MotionEvent.ACTION_UP: {
            }
            case MotionEvent.ACTION_CANCEL: {
            }
        }

        if (flag) {
            return true;
        } else {
            return super.onInterceptTouchEvent(e);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
            mOldSwipedLayout.smoothCloseMenu();
            mOldSwipedLayout = null;
            mOldTouchedPosition = -1;
            return false;
        }
        return super.onTouchEvent(e);
    }

//    使用案例
//    //创建菜单：
//    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
//        @Override
//        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
//           /* SwipeMenuItem deleteItem = new SwipeMenuItem(mContext);
//            // 各种文字和图标属性设置。
//            leftMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。*/
//
//
//            // 在Item右侧添加一个菜单。
//            // 各种文字和图标属性设置。
//            // 2 删除
//            SwipeMenuItem deleteItem = new SwipeMenuItem(MainActivity.this);
//            deleteItem.setText("删除")
//                    .setBackgroundColor(getResources().getColor(R.color.red))
//                    .setTextColor(Color.WHITE) // 文字颜色。
//                    .setTextSize(15) // 文字大小。
//                    .setWidth(300)
//                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//
//            rightMenu.addMenuItem(deleteItem);
//
//            // 注意：哪边不想要菜单，那么不要添加即可。
//        }
//    };
//    // 设置监听器。
//    recycler.setSwipeMenuCreator(mSwipeMenuCreator);
//      SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
//          @Override
//          public void onItemClick(SwipeMenuBridge menuBridge) {
//            //任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
//            menuBridge.closeMenu();
//
//            延迟100毫秒执行（立即执行会出现一瞬的阴影 影响美观）
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
//                    int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
//                    int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//                    list.remove(adapterPosition);
//                    adapter.notifyDataSetChanged();
//                }
//            }, 100);
//
//
//          }
//      };
//      // 菜单点击监听。
//      recycler.setSwipeMenuItemClickListener(mMenuItemClickListener);
//
//      // 必须 最后执行
//      recycler.setAdapter(adapter);


}
