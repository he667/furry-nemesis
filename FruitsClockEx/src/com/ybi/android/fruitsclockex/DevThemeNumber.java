package com.ybi.android.fruitsclockex;

import java.io.File;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;

public class DevThemeNumber implements Number {

	private int numberValue;
	private final String storagePath;

	public DevThemeNumber() {
		File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		path.mkdirs();
		storagePath = path.getAbsolutePath();

	}

	@Override
	public void draw(int xSize, int ySize, int position, Canvas c, Paint p) {
		int pos = position * xSize / 4;

		Bitmap bg;
		try {
			String resID = null;

			if (numberValue == -1) {
				resID = "dev_bg";
				pos = 0;
			} else {
				resID = "dev_0" + numberValue;
			}

			if (resID != null) {
				Log.d(FruitsClockActivity.TAG, "resID = " + resID);
				bg = BitmapFactory.decodeFile(storagePath + "/fruitsclockex/" + resID + ".png");
				c.drawBitmap(bg, pos, 0, p);
			}

		} catch (RuntimeException e) {
			Log.e(FruitsClockActivity.TAG, "resID = " + numberValue);
		}


	}

	@Override
	public DevThemeNumber set(int i) {
		numberValue = i;
		return this;
	}

}
