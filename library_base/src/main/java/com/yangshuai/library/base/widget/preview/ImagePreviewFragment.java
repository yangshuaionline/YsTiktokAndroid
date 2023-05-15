package com.yangshuai.library.base.widget.preview;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.piasy.biv.view.BigImageView;
import com.yangshuai.library.base.BaseBindingAdapter;
import com.yangshuai.library.base.R;

import java.io.File;

/**
 * @Author hcp
 * @Created 4/8/19
 * @Editor hcp
 * @Edited 4/8/19
 * @Type
 * @Layout
 * @Api
 */
public class ImagePreviewFragment extends Fragment {

    private static final String ARGS_ITEM = "args_item";
    private OnFragmentInteractionListener mListener;

    public static ImagePreviewFragment newInstance(String url) {
        ImagePreviewFragment fragment = new ImagePreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_ITEM, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_preview_image_fragm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String url = getArguments().getString(ARGS_ITEM);
        if (url == null) {
            return;
        }

//        ImageViewTouch image = view.findViewById(R.id.image_view);
////        image.setDisplayType(ImageViewTouchBase.DisplayType.NONE);
//        image.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
//            @Override
//            public void onSingleTapConfirmed() {
//                if (mListener != null) {
//                    mListener.onClick();
//                }
//            }
//        });
        BigImageView image = view.findViewById(R.id.itemImage);
        if (url.startsWith("http://") || url.startsWith("https://")) {
            // 网络图片，拼接样式后显示
            image.showImage(Uri.parse(BaseBindingAdapter.getImageUrl(url, BaseBindingAdapter.OssImageStyle.ORIGINAL.style)));
        } else {
            // 本地图片，直接显示
            image.showImage(Uri.fromFile(new File(url)));
        }
//        BaseBindingAdapter.loadImage(image,url);
    }

    public void resetView() {
        if (getView() != null) {
//            ((ImageViewTouch) getView().findViewById(R.id.image_view)).resetMatrix();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
