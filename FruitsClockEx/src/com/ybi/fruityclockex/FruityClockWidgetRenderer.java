package com.ybi.fruityclockex;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

public class FruityClockWidgetRenderer {

	public static void render(Context context, RemoteViews remoteViews) {
		remoteViews.setImageViewBitmap(R.id.imageView, getBitmap(context));
	}

	private static Bitmap getBitmap(Context context) {


		// date preparation
		GregorianCalendar gc = new GregorianCalendar();

		int ijour = gc.get(Calendar.DATE) - 1;
		int isemaine = gc.get(Calendar.DAY_OF_WEEK) - 1;
		int imois = gc.get(Calendar.MONTH);

		int h = gc.get(Calendar.HOUR_OF_DAY);
		int m = gc.get(Calendar.MINUTE);

		// lets write something
		Number hourTen = NumberFactory.getInstance(context).getNumber().set(h / 10);
		Number hour = NumberFactory.getInstance(context).getNumber().set(h - (int) (h / 10.0f) * 10);
		Number minTen = NumberFactory.getInstance(context).getNumber().set(m / 10);
		Number min = NumberFactory.getInstance(context).getNumber().set(m - (int) (m / 10.0f) * 10);

		// lets build it
		Bitmap bitmap = ClockBuilder.buildClock(context, hourTen, hour, minTen, min);

		//		canvas.drawCircle(xSize / 8, ySize / 2, xSize / 8 - 4, p);
		//		canvas.drawCircle(3 * xSize / 8, ySize / 2, xSize / 8 - 4, p);
		//
		//		canvas.drawCircle(4 * xSize / 8, ySize / 2, 2, p);
		//
		//		canvas.drawCircle(5 * xSize / 8, ySize / 2, xSize / 8 - 4, p);
		//		canvas.drawCircle(7 * xSize / 8, ySize / 2, xSize / 8 - 4, p);

		return bitmap;
	}

}
