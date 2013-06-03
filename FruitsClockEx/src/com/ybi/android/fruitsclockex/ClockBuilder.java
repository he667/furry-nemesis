package com.ybi.android.fruitsclockex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;

public class ClockBuilder {

	static Bitmap buildClock(Context context, Number hourTen, Number hour, Number minTen, Number min, Number background) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		//android:minHeight="72dp"
		//android:minWidth="146dp"
		// pixels = dps * (density / 160)
		//		05-29 17:30:00.018: D/FruitsClockEx(12601): Metrics xdpi 319.79
		//		05-29 17:30:00.018: D/FruitsClockEx(12601): Metrics ydpi 318.745
		//		05-29 17:30:00.028: D/FruitsClockEx(12601): Metrics densityDpi 320
		//		05-29 17:30:00.028: D/FruitsClockEx(12601): Metrics xSize 291 = 4 * 73
		//		05-29 17:30:00.028: D/FruitsClockEx(12601): Metrics ySize 143

		//ldpi	Resources for low-density (ldpi) screens (~120dpi).
		//mdpi	Resources for medium-density (mdpi) screens (~160dpi). (This is the baseline density.)
		//hdpi	Resources for high-density (hdpi) screens (~240dpi).
		//xhdpi	Resources for extra high-density (xhdpi) screens (~320dpi).

		// xhdpi = 73x144
		// hdpi = 55x108
		// mdpi = 36x72
		// ldpi = 27x54

		//0.9125
		//0.45

		int xSize = (int) (146f * (metrics.xdpi / 160f));
		int ySize = (int) (72f * (metrics.ydpi / 160f));
		Log.d(FruitsClockActivity.TAG, "Metrics xdpi " + metrics.xdpi);
		Log.d(FruitsClockActivity.TAG, "Metrics ydpi " + metrics.ydpi);
		Log.d(FruitsClockActivity.TAG, "Metrics densityDpi " + metrics.densityDpi);
		Log.d(FruitsClockActivity.TAG, "Metrics xSize " + xSize);
		Log.d(FruitsClockActivity.TAG, "Metrics ySize " + ySize);

		// bitmap preparation
		Bitmap bitmap = Bitmap.createBitmap(xSize, ySize, Config.ARGB_8888);
		bitmap.setDensity(metrics.densityDpi);

		// canvas preparation
		Canvas canvas = new Canvas(bitmap);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setSubpixelText(true);
		p.setStyle(Paint.Style.FILL);
		p.setColor(Color.WHITE);

		background.draw(xSize, ySize, -1, canvas, p);

		hourTen.draw(xSize, ySize, 0, canvas, p);
		hour.draw(xSize, ySize, 1, canvas, p);

		minTen.draw(xSize, ySize, 2, canvas, p);
		min.draw(xSize, ySize, 3, canvas, p);

		return bitmap;
	}

	//public void hourTen(Number n);
	//
	//public void hour(Number n);
	//
	//public void minTen(Number n);
	//
	//public void min(Number n);

	//public Clock getClock();

}
