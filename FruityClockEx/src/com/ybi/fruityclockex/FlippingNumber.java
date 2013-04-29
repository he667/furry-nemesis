package com.ybi.fruityclockex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class FlippingNumber implements Number {

	private final static String PATH = "/mnt/sdcard/FruityClockEx/Themes/FlippingNumber/";

	private int numberValue;

	@Override
	public void draw(int xSize, int ySize, int position, Canvas c, Paint p) {
		int pos = position * xSize / 4;

		Bitmap bg;
		if (numberValue == 0) {
			bg = BitmapFactory.decodeFile(PATH + "1_10.png");
		} else {
			bg = BitmapFactory.decodeFile(PATH + "1_0" + numberValue + ".png");
		}
		c.drawBitmap(bg, pos, 0, p);

	}

	@Override
	public FlippingNumber set(int i) {
		numberValue = i;
		return this;
	}

}
