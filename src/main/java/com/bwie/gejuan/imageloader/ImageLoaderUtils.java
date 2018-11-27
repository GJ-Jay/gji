package com.bwie.gejuan.imageloader;

import android.graphics.Bitmap;

import com.bwie.gejuan.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ImageLoaderUtils {
    //设置网络图片格式 及允许缓存到sd卡
    public static DisplayImageOptions getDisplayImageOptions() {
        DisplayImageOptions build = new DisplayImageOptions.Builder()
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        return build;
    }
}
