package com.liub.baselibrary.utils;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.liub.baselibrary.R;

/**
 * glide 帮助类
 */
public class GlideUtils {
    /**
     * 加载常规图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void load(Context context, String path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_action_back)
                .error(R.mipmap.ic_action_back);
        Glide.with(context).load(path).apply(requestOptions).into(imageView);
    }

    /**
     * 加载成圆角图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadRoundCorner(Context context, String path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_action_back)
                .error(R.mipmap.ic_action_back)
                .transform(new RoundedCorners(10));
        Glide.with(context).load(path).apply(requestOptions).into(imageView);
    }


    /**
     * 加载成圆角图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadRoundCorner(Context context, String path, ImageView imageView, int defaultResId) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(defaultResId)
                .error(defaultResId)
                .transform(new RoundedCorners(10));
        Glide.with(context).load(path).apply(requestOptions).into(imageView);
    }

    /**
     * 加载成顶部圆角图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadTopRoundCorner(Context context, String path, ImageView imageView) {
//        CornerTransform transformation = new CornerTransform(context, DisplayUtil.dp2px(context, 5));
//        //只是绘制左上角和右上角圆角
//        transformation.setExceptCorner(false, false, true, true);
//        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.image_default)
//                .error(R.drawable.image_default)
//                .transform(transformation);
//        Glide.with(context).load(path).apply(requestOptions).into(imageView);
    }

    /**
     * 加载成圆型图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadRoundCircle(Context context, String path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_action_back)
                .error(R.mipmap.ic_action_back)
                .transform(new CircleCrop());
        Glide.with(context).load(path).apply(requestOptions).into(imageView);
    }

    /**
     * 加载成圆型bitmap图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadRoundCircle(Context context, byte[] path, ImageView imageView, int defaultImageId) {
//        RoundedCorners roundedCorners= new RoundedCorners(Math.round(ScreenUtils.dpi2Px(8f)));
        CornerTransform cornerTransform=new CornerTransform(context,Math.round(ScreenUtils.dpi2Px(8f)));
        cornerTransform.setExceptCorner(false,false,false,false);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(defaultImageId)
                .error(defaultImageId)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(cornerTransform);
        Glide.with(context).asBitmap().load(path).apply(requestOptions).into(imageView);
    }

    /**
     * 加载圆型图片
     *
     * @param context
     * @param path
     * @param imageView
     * @param defaultImageId
     */
    public static void loadRoundCircle(Context context, String path, ImageView imageView, int defaultImageId) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(defaultImageId)
                .error(defaultImageId)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new CircleCrop());
        Glide.with(context).load(path).apply(requestOptions).into(imageView);
    }

    /**
     * 加载gif
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadGif(Context context, int path, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context).asGif().load(path).apply(requestOptions).into(imageView);
    }

}