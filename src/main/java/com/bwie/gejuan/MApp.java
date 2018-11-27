package com.bwie.gejuan;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class MApp extends Application {
    //配置缓存目录
    File file = new File(Environment.getExternalStorageDirectory()+"/"+"img");

    @Override
    public void onCreate() {
        super.onCreate();
        //ImageLoader全局配置
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(file))
                .build();
        //获取实例
        ImageLoader instance = ImageLoader.getInstance();
        instance.init(build);
    }
}
