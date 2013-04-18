package com.ybi.fruityclockex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.DisplayMetrics;

public class ClockBuilder {

	static Bitmap buildClock(Context context, Number hourTen, Number hour, Number minTen, Number min) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		//android:minHeight="72dp"
		//android:minWidth="146dp"
		// pixels = dps * (density / 160)
		int xSize = (int) (146f * (metrics.xdpi / 160f));
		int ySize = (int) (72f * (metrics.ydpi / 160f));

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

		p.setAlpha(50);
		canvas.drawRect(0, 0, xSize, ySize, p);
		p.setAlpha(255);

		hourTen.draw(xSize, ySize, 1, canvas, p);
		hour.draw(xSize, ySize, 3, canvas, p);

		minTen.draw(xSize, ySize, 5, canvas, p);
		min.draw(xSize, ySize, 7, canvas, p);

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
