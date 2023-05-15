package com.yangshuai.library.base.view;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;
import com.yangshuai.library.base.BaseBindingAdapter;
import com.yangshuai.library.base.R;
import com.yangshuai.library.base.utils.YJGlideUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author hcp
 * @Created 7/15/19
 * @Editor hcp
 * @Edited 7/15/19
 * @Type
 * @Layout
 * @Api
 */
public class PreviewRecyclerView extends RecyclerView {

    private PreviewRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<String> images;

    public PreviewRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public PreviewRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        images = new ArrayList<>();
        adapter = new PreviewRecyclerAdapter(images);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        setLayoutManager(layoutManager);
        setAdapter(adapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this);
    }

    /**
     * 设置图片
     *
     * @param images
     */
    public void setImages(List<String> images) {

        if (images != null && images.size() > 0) {
            this.images.clear();
            this.images.addAll(images);
        }
    }

    /**
     * 设置图片点击监听
     *
     * @param onItemClickListener 图片点击监听器
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        adapter.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 长按监听
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        adapter.setOnItemLongClickListener(onItemLongClickListener);
    }

    /**
     * 设置图片切换监听
     *
     * @param onPageChangeListener 图片切换监听器
     */
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int index = layoutManager.findFirstVisibleItemPosition();
                onPageChangeListener.onChanged(index);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    class PreviewRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<String> imageUrls;
        private OnItemClickListener onItemClickListener;
        private OnItemLongClickListener onItemLongClickListener;

        public PreviewRecyclerAdapter(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
            this.onItemLongClickListener = onItemLongClickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.base_preview_image, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(imageUrls.get(position));
            holder.imgVideo.setOnClickListener(v -> {
                if (onItemClickListener != null) onItemClickListener.onClick(position);
            });
            holder.itemImage.setOnClickListener(v -> {
                if (onItemClickListener != null) onItemClickListener.onClick(position);
            });
            holder.itemImage.setOnLongClickListener(v -> {
                // 长按保存图片
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(position);
                    return true;
                }
                return false;
            });

            holder.imgVideo.setOnLongClickListener(v -> {
                // 长按保存视频
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(position);
                    return true;
                }
                return false;
            });
        }

        @Override
        public int getItemCount() {
            return imageUrls.size();
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);
            holder.clear();
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            if (holder.hasNoImage()) {
                holder.rebind();
            }
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;
        private BigImageView itemImage;
        private String imageUrl;
        private ImageView imgVideo, imgVideoFlag, image_null;

        ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            itemImage = itemView.findViewById(R.id.itemImage);
            imgVideo = itemView.findViewById(R.id.img_video);
            imgVideoFlag = itemView.findViewById(R.id.img_flag_video);
            image_null = itemView.findViewById(R.id.image_null);
            itemImage.setTapToRetry(false);
        }

        void bind(String imageUrl) {
            this.imageUrl = imageUrl;
            rebind();
        }

        void rebind() {
            if (imageUrl == null) {
                itemImage.setVisibility(GONE);
                imgVideoFlag.setVisibility(GONE);
                imgVideo.setVisibility(GONE);
                image_null.setVisibility(VISIBLE);
                return;
            }

            image_null.setVisibility(GONE);

            // 显示视频
            if (imageUrl.contains("#x-oss-process-video")) {
                itemImage.setVisibility(GONE);
                imgVideoFlag.setVisibility(VISIBLE);
                imgVideo.setVisibility(VISIBLE);
                BaseBindingAdapter.loadImage(imgVideo, imageUrl);
            } else {
                itemImage.setVisibility(VISIBLE);
                imgVideoFlag.setVisibility(GONE);
                imgVideo.setVisibility(GONE);
                if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                    if (imageUrl.contains("/erp/im/") || imageUrl.contains("/C_IM/")
                            || imageUrl.contains("images-beta.yjyz.com")
                            || imageUrl.contains("images.yjyz.com")
                            || imageUrl.contains("/erp/live/") || imageUrl.contains("/notice/")
                            || imageUrl.contains("erp/contract/aftermarket/submitorder")) {
                        // nothing
                    }
                    else if (YJGlideUtil.isYJImageUrl(imageUrl) && !imageUrl.contains("?x-oss-process")) {
                        imageUrl += "?x-oss-process=style/centerlogo";
                    }
                    itemImage.setImageViewFactory(new GlideImageViewFactory());
                    itemImage.showImage(Uri.parse(imageUrl));
                    itemImage.setImageLoaderCallback(new ImageLoader.Callback() {
                        @Override
                        public void onCacheHit(int imageType, File image) {

                        }

                        @Override
                        public void onCacheMiss(int imageType, File image) {

                        }

                        @Override
                        public void onStart() {
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onProgress(int progress) {

                        }

                        @Override
                        public void onFinish() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onSuccess(File image) {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFail(Exception error) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });



                } else if (imageUrl.startsWith("house_type_empty")) {
                    itemImage.showImage(Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.mipmap.new_house_detail_house_null));
                } else {
                    itemImage.showImage(Uri.fromFile(new File(imageUrl)));
                }
            }

        }

        void clear() {
            SubsamplingScaleImageView ssiv = itemImage.getSSIV();
            if (ssiv != null) {
                ssiv.recycle();
            }
        }

        boolean hasNoImage() {
            SubsamplingScaleImageView ssiv = itemImage.getSSIV();
            return ssiv == null || !ssiv.hasImage();
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public interface OnPageChangeListener {
        void onChanged(int position);
    }

    public interface OnItemLongClickListener {
        void onLongClick(int position);
    }
}
