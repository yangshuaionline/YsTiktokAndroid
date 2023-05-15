package com.yangshuai.library.base;

import android.graphics.Color;
import android.net.Uri;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yangshuai.library.base.utils.DecimalAndIntegerFilter;
import com.yangshuai.library.base.utils.DecimalDigitsInputFilter;
import com.yangshuai.library.base.utils.Utils;
import com.yangshuai.library.base.utils.YJGlideUtil;
import com.yangshuai.library.base.widget.RoundedCornersTransformation;

import java.io.File;

/**
 * DataBinding中一些通用的绑定方法都写到这里实现
 *
 * @author hcp
 * @created 2019/4/23
 */
public class BaseBindingAdapter {

    /**
     * // 大图样式
     * large = "centerlogo375x304"
     * // 小图样式
     * small = "centerlogo86x60"
     * // 列表样式
     * listSmall = "centerlogo105x85"
     * // 头像样式
     * avatar = "centerlogo48x48"
     * // 原图带水印
     * ORIGINAL centerlogo
     * <p>
     * //带水印bucket(yjyz-beta-sz、yjyz-sz)样式后缀格式
     * // 为：?x-oss-process=style/centerlogo{宽}x{高}，
     * // 如：120×86 ---> ?x-oss-process=style/centerlogo120x86 注意这里中间的是小写的字母x，不是数学乘号！！
     * <p>
     * //不带水印bucket(yjyz-beta-images、yjyz-images)样式后缀格式
     * // 为：?x-oss-process=image/auto-orient,1/resize,m_fill,w_{宽},h_{高}/quality,q_90，
     * // 如：120×86 ---> ?x-oss-process=image/auto-orient,1/resize,m_fill,w_120,h_96/quality,q_90
     */

    public enum OssImageStyle {
        //static-开头，有水印
        LARGE("centerlogo750x608"),
        SMALL("centerlogo172x120"),
        //        LIST_SMALL("centerlogo375x304"),//作废
//        AVATAR("centerlogo96x96"),//作废
        ORIGINAL("centerlogo"), // 有水印原图
        BLANK("blank"), // 无水印-static 开头
        BLANK48("blank48x48"),// 无水印 images开头，原图无水印

        //2020.05 前端小图加载优化
        // https://wiki.yjyz.com/pages/viewpage.action?pageId=5751900
        NEWSIZE_120x124("centerlogo120x124"),
        NEWSIZE_156x156("centerlogo156x156"),
        NEWSIZE_172x120("centerlogo172x120"),//SMALL
        NEWSIZE_180x180("centerlogo180x180"),
        NEWSIZE_210x170("centerlogo210x170"),
        NEWSIZE_220x170("centerlogo220x170"),//小区、新房、二手房、租房列表
        NEWSIZE_380x284("centerlogo380x284"),
        NEWSIZE_596x416("centerlogo596x416"),
        NEWSIZE_750x468("centerlogo750x468"),
        NEWSIZE_750x608("centerlogo750x608"),//LARGE
        NEWSIZE_750x816("centerlogo750x816");


        public String style;

        OssImageStyle(String style) {
            this.style = style;
        }
    }


    /**
     * 根据API返回的图片链接拼接对应的样式
     *
     * @param apiUrl     API返回的图片地址
     * @param imageStyle 需要拼接的样式，可选类型参考上面的OssImageStyle枚举
     * @return 返回能显示的图片地址
     */
    public static String getImageUrl(String apiUrl, String imageStyle) {
        String ossUrl = "";
        if (TextUtils.isEmpty(apiUrl)) {
            return ossUrl;
        }
        if (apiUrl.contains("?x-oss-process")) {
            // 如果含有拼接参数的话，就直接用API返回的
            ossUrl = apiUrl;
        } else {
            // 没有包含样式，APP自己拼接
//            if (apiUrl.contains(AppBaseConfig.get().getConfig().getBaseUrl())
//                    || apiUrl.contains("static-beta.yjyz.com")
//                    || apiUrl.contains("static-stag.yjyz.com")
//                    || apiUrl.contains("static.yjyz.com")) {

            if (apiUrl.contains("static-beta.yjyz.com")
                    || apiUrl.contains("static.yjyz.com")) {
                // 是我们自己的域名才拼接，外链直接显示
                /**
                 * 1、带水印bucket(yjyz-beta-sz、yjyz-sz)样式后缀格式为：?x-oss-process=style/centerlogo{宽}x{高}，
                 * 如：120×86 ---> ?x-oss-process=style/centerlogo120x86 注意这里中间的是小写的字母x，不是数学乘号！！
                 * 对应域名：https://static.yjyz.com (生产)
                 * https://static-beSta.yjyz.com (测试)
                 */
                ossUrl = apiUrl + "?x-oss-process=style/" + imageStyle;


            } else if (apiUrl.contains("images-beta.yjyz.com")
                    || apiUrl.contains("images.yjyz.com")) {
                /**
                 * 2、不带水印bucket(yjyz-beta-images、yjyz-images)样式后缀格式为：
                 * 1）传参式宽高：?x-oss-process=image/auto-orient,1/resize,m_fill,w_{宽},h_{高}/quality,q_90，
                 * 如：120×86 ---> ?x-oss-process=image/auto-orient,1/resize,m_fill,w_120,h_96/quality,q_90
                 * 2）样式别名：?x-oss-process=style/blank{宽}x{高}，如：48×48 ---> ?x-oss-process=style/blank48x48
                 * 注意这里中间的是小写的字母x，不是数学乘号！！
                 * (注：目前无水印bucket仅添加了blank48x48一种样式别名)
                 * 对应域名：
                 * https://images.yjyz.com (生产)
                 * https://images-beta.yjyz.com (测试)
                 */
                ossUrl = apiUrl;
            } else {
                ossUrl = apiUrl;
            }
        }
        return ossUrl;
    }


    /**
     * 加载本地图片
     *
     * @param imageView 要显示图片的ImageView
     * @param imagePath 图片URL
     */
    @BindingAdapter({"imageLocatePath"})
    public static void loadLocateImage(ImageView imageView, String imagePath) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_placeholder_youju)
                .error(R.mipmap.icon_placeholder_youju)
                .centerCrop(); // 不加这句会导致未使用圆形的图片也会变圆
        if (TextUtils.isEmpty(imagePath)) {
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(R.mipmap.icon_placeholder_empty)
                    .fitCenter()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(new File(imagePath))
                    .fitCenter()
                    .into(imageView);
        }

    }


    /**
     * 加载图片(带水印稍大图)
     *
     * @param imageView 要显示图片的ImageView
     * @param url       图片URL
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_placeholder_youju)
                .error(R.mipmap.icon_placeholder_youju)
                .centerCrop(); // 不加这句会导致未使用圆形的图片也会变圆
        if (TextUtils.isEmpty(url)) {
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(R.mipmap.icon_placeholder_empty)
                    .fitCenter()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(YJGlideUtil.getGlideUrl(getImageUrl(url.trim(), OssImageStyle.NEWSIZE_750x816.style)))
                    .fitCenter()
                    .into(imageView);
        }

    }

    /**
     * 加载图片(带水印原图)
     *
     * @param imageView 要显示图片的ImageView
     * @param url       图片URL
     */
    @BindingAdapter({"imageUrlAdd2"})
    public static void loadImageAndDefault(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_placeholder_youju)
                .error(R.mipmap.icon_placeholder_youju)
                .transform(new CenterCrop(), new RoundedCornersTransformation(Utils.dp2Px(imageView.getContext(), 4), 0));
        if (TextUtils.isEmpty(url)) {
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(R.mipmap.icon_image_add)
                    .fitCenter()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.NEWSIZE_380x284.style)))
                    .into(imageView);
        }
    }


    /**
     * 加载视频封面
     */
    @BindingAdapter(("videoUrl"))
    public static void loadVideoCover(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_placeholder_youju)
                .error(R.mipmap.icon_placeholder_youju)
                .centerCrop(); // 不加这句会导致未使用圆形的图片也会变圆
        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(options)
                .load(YJGlideUtil.getGlideUrl(url))
                .into(imageView);
    }

    /**
     * 加载房源详情封面图
     */
    @BindingAdapter(("houseDetailImage"))
    public static void loadHouseDetailCover(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_placeholder_youju)
                .error(R.mipmap.icon_placeholder_youju)
                .centerCrop(); // 不加这句会导致未使用圆形的图片也会变圆
        if (TextUtils.isEmpty(url)) {
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(R.mipmap.icon_placeholder_no_img)
                    .fitCenter()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.ORIGINAL.style)))
                    .fitCenter()
                    .into(imageView);
        }
    }

    /**
     * 图片基础加载+默认图
     *
     * @param imageView 要显示图片的ImageView
     * @param url       图片链接
     * @param style     图片oss样式
     */
    private static void baseLoadImageWithDefault(ImageView imageView, String url, String style) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_placeholder_youju)
                .error(R.mipmap.icon_placeholder_youju)
                .centerCrop(); // 不加这句会导致未使用圆形的图片也会变圆
        if (TextUtils.isEmpty(url)) {
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(R.mipmap.icon_placeholder_empty)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(YJGlideUtil.getGlideUrl(getImageUrl(url, style)))
                    .into(imageView);
        }
    }

    /**
     * 加载带有OSS样式的大图
     */
    @BindingAdapter({"imageUrlLarge"})
    public static void loadImageLarge(ImageView imageView, String url) {
        baseLoadImageWithDefault(imageView, url, OssImageStyle.LARGE.style);
    }

    /**
     * 加载带有OSS样式的小图
     */
    @BindingAdapter({"imageUrlSmall"})
    public static void loadImageSmall(ImageView imageView, String url) {
        baseLoadImageWithDefault(imageView, url, OssImageStyle.SMALL.style);
    }

    /**
     * 加载图片(带水印),图片尺寸380x284
     *
     * @param imageView 要显示图片的ImageView
     * @param url       图片URL
     */
    @BindingAdapter({"imageUrlAdd"})
    public static void loadImage380X284(ImageView imageView, String url) {
        baseLoadImageWithDefault(imageView, url, OssImageStyle.NEWSIZE_380x284.style);
    }
    /**
     * 加载带有OSS样式的列表图片 房源申请举报专用
     */
    @BindingAdapter({"imageUrlListRe"})
    public static void loadImageListRe(ImageView imageView, String url) {
        loadImageListBaseRe(imageView,url,R.mipmap.icon_placeholder_no_img,R.mipmap.icon_placeholder_youju);
    }

    /**
     * 加载带有OSS样式的列表图片
     */
    @BindingAdapter({"imageUrlList"})
    public static void loadImageList(ImageView imageView, String url) {
        loadImageListBase(imageView,url,R.mipmap.icon_placeholder_no_img,R.mipmap.icon_placeholder_youju);
    }


    /**
     * 加载带有OSS样式的列表图片(新房)
     */
    @BindingAdapter({"imageUrlListNewHouse"})
    public static void imageUrlListNewHouse(ImageView imageView, String url) {
        loadImageListBase(imageView,url,R.mipmap.new_house_project_list_null,R.mipmap.new_house_project_list_null);
    }


    /**
     * 加载带有OSS样式的列表图片(基础)
     *
     * @param imageView  显示图片的view
     * @param url        图片链接
     * @param emptyResId 空布局图片资源
     * @param errorResId 错误图片资源
     */
    public static void loadImageListBase(ImageView imageView, String url, int emptyResId, int errorResId) {
        RequestOptions options = new RequestOptions()
                .placeholder(errorResId)
                .error(errorResId)
                .centerCrop(); // 不加这句会导致未使用圆形的图片也会变圆
        if (TextUtils.isEmpty(url)) {
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(emptyResId)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.NEWSIZE_220x170.style)))
                    .into(imageView);
        }
    }

    /**
     * 加载圆角 房源申请举报专用
     *
     * @param imageView  显示图片的view
     * @param url        图片链接
     * @param emptyResId 空布局图片资源
     * @param errorResId 错误图片资源
     */
    public static void loadImageListBaseRe(ImageView imageView, String url, int emptyResId, int errorResId) {
        RequestOptions options = new RequestOptions()
                .placeholder(errorResId)
                .error(errorResId)
                .transform(new CenterCrop(), new RoundedCornersTransformation(Utils.dp2Px(imageView.getContext(), 4), 0));
        if (TextUtils.isEmpty(url)) {
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(emptyResId)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.NEWSIZE_220x170.style)))
                    .into(imageView);
        }
    }
    /**
     * 加载圆角图片 并等比例缩放
     */
    @BindingAdapter({"imageUrlRadius"})
    public static void loadImageRadius(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_placeholder_youju)
                .error(R.mipmap.icon_placeholder_youju)
                .transform(new CenterCrop(), new RoundedCornersTransformation(Utils.dp2Px(imageView.getContext(), 4), 0));
        if (TextUtils.isEmpty(url)) {
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(R.mipmap.icon_placeholder_no_img)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.NEWSIZE_220x170.style)))
                    .into(imageView);
//            Glide.with(imageView.getContext())
//                    .asBitmap()
//                    .load(getImageUrl(url, OssImageStyle.LIST_SMALL.style))
//                    .into(new TransformationUtils(imageView));

        }
    }

    /**
     * 加载本地图片 并等比例缩放
     *
     * @param imageView 要显示图片的ImageView
     * @param imagePath 图片URL
     */
    @BindingAdapter({"imageLocatePathRadius"})
    public static void loadLocateImageRadius(ImageView imageView, String imagePath) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_placeholder_youju)
                .error(R.mipmap.icon_placeholder_youju)
                .transform(new CenterCrop(), new RoundedCornersTransformation(Utils.dp2Px(imageView.getContext(), 4), 0));

        if (TextUtils.isEmpty(imagePath)) {
            // 没有图片6
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(R.mipmap.icon_placeholder_empty)
                    .fitCenter()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(Uri.fromFile(new File(imagePath)))
                    .fitCenter()
                    .into(imageView);
//            Glide.with(imageView.getContext())
//                    .asBitmap()
//                    .load(Uri.fromFile(new File(imagePath)))
//                    .into(new TransformationUtils(imageView));
        }

    }

    /**
     * 加载带有OSS样式的列表图片
     */
    @BindingAdapter({"imageUrlPoster"})
    public static void loadPosterImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.icon_placeholder_poster)
                .error(R.mipmap.icon_placeholder_poster)
                .centerCrop(); // 不加这句会导致未使用圆形的图片也会变圆
        if (TextUtils.isEmpty(url)) {
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(R.mipmap.icon_placeholder_poster)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.NEWSIZE_220x170.style)))
                    .into(imageView);
        }
    }

    /**
     * 加载带有OSS样式的圆形头像
     */
    @BindingAdapter({"userAvatarUrlOss"})
    public static void loadAvatarIconOss(ImageView imageView, String url) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform()
                .placeholder(R.mipmap.icon_portrait)
                .error(R.mipmap.icon_portrait);

        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.BLANK.style)))
                .into(imageView);

    }

    /**
     * 加载带有OSS样式的方形头像
     */
    @BindingAdapter({"userSquareAvatarUrlOss"})
    public static void loadSquareAvatarIconOss(ImageView imageView, String url) {
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new RoundedCorners(20))
                .placeholder(R.mipmap.icon_user_square_portrait)
                .error(R.mipmap.icon_user_square_portrait);

        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.BLANK.style)))
                .into(imageView);

    }

    /**
     * 加载带有OSS样式的方形头像
     */
    @BindingAdapter({"shareSquareAvatarUrlOss"})
    public static void loadShareSquareAvatarIconOss(ImageView imageView, String url) {
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new RoundedCorners(20))
                .placeholder(R.mipmap.icon_portrait)
                .error(R.mipmap.icon_portrait);

        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.BLANK.style)))
                .into(imageView);

    }

    /**
     * 加载圆形头像(注意：ImageView不能设置centerInside)
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"userAvatarUrl"})
    public static void loadAvatarIcon(ImageView imageView, String url) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform()
                .placeholder(R.mipmap.icon_portrait)
                .error(R.mipmap.icon_portrait);

        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.BLANK.style)))
                .into(imageView);

    }

    /**
     * 加载原图，不加水印，不加错误或占位图
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"imgUrlBlank"})
    public static void imgUrlBlank(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Glide.with(imageView.getContext())
                .load(YJGlideUtil.getGlideUrl(getImageUrl(url, BaseBindingAdapter.OssImageStyle.BLANK.style)))
                .into(imageView);

    }

    /**
     * 加载圆形图片
     *
     * @param imageView
     * @param url
     */
    @BindingAdapter({"roundImage"})
    public static void loadCircleIcon(ImageView imageView, String url) {
        RequestOptions requestOptions = RequestOptions.circleCropTransform()
                .placeholder(R.mipmap.icon_round_logo)
                .error(R.mipmap.icon_round_logo);

        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(YJGlideUtil.getGlideUrl(getImageUrl(url, OssImageStyle.BLANK.style)))
                .into(imageView);

    }

    /**
     * 设置view的选中状态
     *
     * @param view
     * @param selected
     */
    @BindingAdapter({"selected"})
    public static void setSelected(View view, boolean selected) {
        view.setSelected(selected);
    }

    /**
     * 设置经纪人性别图标
     *
     * @param imageView
     * @param sex       传字符串或者数字 男 女(或者 1男 2女)
     */
    @BindingAdapter("sexIcon")
    public static void setSexIcon(ImageView imageView, String sex) {
        if (!TextUtils.isEmpty(sex)) {
            if ("男".equals(sex) || "1".equals(sex)) {
                imageView.setBackgroundResource(R.mipmap.icon_sex_boy);
            } else if ("女".equals(sex) || "2".equals(sex)) {
                imageView.setBackgroundResource(R.mipmap.icon_sex_girl);
            }
        }
    }

    /**
     * 设置本地资源
     */
    @BindingAdapter({"background"})
    public static void setBackgroud(ImageView imageView, int icon) {
        imageView.setImageResource(icon);
    }

    @BindingAdapter({"text_color"})
    public static void setTextColor(TextView textView, String color) {
        textView.setTextColor(Color.parseColor(color));
    }

    @BindingAdapter({"itemDecoration"})
    public static void setItemDecoration(RecyclerView recyclerView, RecyclerView.ItemDecoration decoration) {
        recyclerView.addItemDecoration(decoration);
    }


    /**
     * 限制输入框可输入的小数点位数
     *
     * @param digits 最大允许输入的小数位数
     */
    @BindingAdapter({"digits_input"})
    public static void setDecimalDigitsInput(EditText editText, int digits) {
        if (editText != null) {
            editText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(digits)});
        }
    }

    /**
     * 限制输入框可输入的整数和小数的位数(如：只能输入6位整数和2位小数的需求)
     *
     * @param integerCount  最大允许输入的整数位数
     * @param decimalsCount 最大允许输入的小数位数
     */
    @BindingAdapter(value = {"integerCount", "decimalsCount"}, requireAll = true)
    public static void setIntegerAndDigitsInput(EditText editText, int integerCount, int decimalsCount) {
        if (editText != null) {
            editText.setFilters(new InputFilter[]{
                    new DecimalAndIntegerFilter(integerCount, decimalsCount)});
        }
    }






    /**
     * 加载圆角图片 并等比例缩放
     */
    @BindingAdapter({"imageRecRadius"})
    public static void loadImageRadiusrec(ImageView imageView,int url) {
        RequestOptions options = new RequestOptions()
                .transform(new CenterCrop(), new RoundedCornersTransformation(Utils.dp2Px(imageView.getContext(), 4), 0));
            // 没有图片
            Glide.with(imageView.getContext())
                    .applyDefaultRequestOptions(options)
                    .load(R.mipmap.app_list_pic)
                    .into(imageView);
    }
}

