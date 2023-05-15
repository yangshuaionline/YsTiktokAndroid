package com.yangshuai.module.video.adapter;

import android.content.Context;
import android.util.Log;


import com.yangshuai.library.base.application.BaseApplication;
import com.yangshuai.library.base.db.VideoDB;
import com.yangshuai.library.base.view.recycleview.BaseRecycleAdapter;
import com.yangshuai.library.base.view.recycleview.BaseViewHolder;
import com.yangshuai.module.video.R;
import com.yangshuai.module.video.bean.PlatformlVideoBean;
import com.yangshuai.module.video.jzvideo.JZDataSource;
import com.yangshuai.module.video.jzvideo.Jzvd;
import com.yangshuai.module.video.jzvideo.JzvdStd;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;

import java.util.LinkedHashMap;
import java.util.List;

public class VideoTiktokRecyclerViewAdapter extends BaseRecycleAdapter<PlatformlVideoBean.RecordsBean> {
    private Context context;
    public VideoTiktokRecyclerViewAdapter(Context context, List<PlatformlVideoBean.RecordsBean> dataList) {
        super(context, dataList);
        this.context = context;
    }

    @Override
    protected boolean supportDataBinding() {
        return false;
    }

    @Override
    protected int bindItemLayout() {
        return R.layout.video_tiktok_item;
    }

    @Override
    protected void bindData(BaseViewHolder holder, PlatformlVideoBean.RecordsBean data, int position) {
        Log.d("=====>走一次","================>"+position);
        String proxyUrl = null;
        try {
            LinkedHashMap map = new LinkedHashMap();
            proxyUrl = BaseApplication.getProxy(context).getProxyUrl(data.getVideoUrl());
            //        map.put("高清", proxyUrl);
            //        map.put("标清", VideoConstant.videoUrls[0][6]);
            //        map.put("普清", VideoConstant.videoUrlList[0]);
        }catch (Exception e){
            Log.d("=====>视频缓存异常",e.getMessage().toString());
            proxyUrl = data.getVideoUrl();
        }
        try {
            JZDataSource jzDataSource = new JZDataSource(proxyUrl);
            jzDataSource.looping = true;//设置循环播放
            JzvdStd jzVideo = holder.itemView.findViewById(R.id.jz_video);
            jzVideo.setUp(jzDataSource,Jzvd.SCREEN_FULLSCREEN);
        }catch (Exception e){
            Log.d("=====>视频播放异常",e.getMessage().toString());
        }

//        BaseBindingAdapter.loadImage(jzVideo.posterImageView, data.getCover());
//        BaseBindingAdapter.loadImage(jzVideo.posterImageView, data.getVideoUrl());
        try {
//            List<VideoDB> resources = LitePal.where("key ="+position).find(VideoDB.class);
//            if(resources.isEmpty()){
//
//            }else{
//                for(int i = 0;i<resources.size();i++){
//                    VideoDB db = resources.get(i);
//                    db.setAgree(111);
//                    db.setAgree(true);
//                    db.setCollect(222);
//                    db.setCollect(false);
//                    db.setImg(data.getCover());
//                    db.setUrl(proxyUrl);
//                    db.updateAsync(1).listen(new UpdateOrDeleteCallback() {
//                        @Override
//                        public void onFinish(int rowsAffected) {
//                            Log.d("=====>","更新结果"+rowsAffected);
//                            logAll();
//                        }
//                    });
//                }
//            }
            VideoDB db = new VideoDB();
            db.setKey(position);
            db.setAgree(111);
            db.setAgree(true);
            db.setCollect(222);
            db.setCollect(false);
            db.setImg(data.getCover());
            db.setUrl(proxyUrl);
            db.saveOrUpdateAsync("key = "+position).listen(new SaveCallback() {
                @Override
                public void onFinish(boolean success) {
                    Log.d("=====>","保存结果"+success);
                    logAll();
                }
            });

        }catch (Exception e){
            Log.d("=====>视频SQL存储异常",e.getMessage().toString());
        }

    }

    private void logAll(){
        LitePal.findAllAsync(VideoDB.class).listen(new FindMultiCallback<VideoDB>() {
            @Override
            public void onFinish(List<VideoDB> list) {
                Log.d("=====>","SQL中数据总数"+list.size());
                //查询到的结果集将回调到此方法中
                for(int i = 0;i<list.size();i++){
                    Log.d("=====>","|||"+list.get(i).getKey()+"|||"+list.get(i).getUrl());
                }
            }
        });
    }
}
