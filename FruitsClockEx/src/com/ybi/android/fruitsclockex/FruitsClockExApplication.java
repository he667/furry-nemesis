package com.ybi.android.fruitsclockex;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class FruitsClockExApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		//File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		DisplayImageOptions options =
				new DisplayImageOptions.Builder().resetViewBeforeLoading().cacheInMemory().cacheOnDisc()
				.displayer(new FadeInBitmapDisplayer(200))
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		.memoryCacheExtraOptions(480, 800)
		// default = device screen dimensions
		.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75)
		.threadPoolSize(3)
		.defaultDisplayImageOptions(options)
		// default
		.denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 * 1024 * 1024))
		.memoryCacheSize(2 * 1024 * 1024).discCacheSize(50 * 1024 * 1024).discCacheFileCount(100).enableLogging()
		.build();

		ImageLoader.getInstance().init(config);
	}
}
