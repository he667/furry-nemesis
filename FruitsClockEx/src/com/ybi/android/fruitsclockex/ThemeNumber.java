package com.ybi.android.fruitsclockex;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class ThemeNumber implements Number {

	private int numberValue;
	private final String themeLink;
	private final Context context;

	public ThemeNumber(Context context, String themeLink) {
		this.themeLink = themeLink;
		this.context = context;
	}

	@Override
	public void draw(int xSize, int ySize, int position, Canvas c, Paint p) {
		int pos = position * xSize / 4;

		Resources resources;
		Bitmap bg;
		try {
			PackageManager manager = context.getPackageManager();
			resources = manager.getResourcesForApplication(themeLink);

			int resID;

			if (numberValue == -1) {
				resID = resources.getIdentifier("tbg", "drawable", themeLink);
				pos = 0;
			} else if (numberValue == 0) {
				resID = resources.getIdentifier("t1_10", "drawable", themeLink);
			} else {
				resID = resources.getIdentifier("t1_0" + numberValue, "drawable", themeLink);
			}

			if (resID != 0) {
				Log.d(FruitsClockActivity.TAG, "resID = " + resID);
				bg = BitmapFactory.decodeResource(resources, resID);
				c.drawBitmap(bg, pos, 0, p);
			}

		} catch (NameNotFoundException e) {
			Log.e(FruitsClockActivity.TAG, "resID = " + numberValue);
		}


	}

	@Override
	public ThemeNumber set(int i) {
		numberValue = i;
		return this;
	}

}
