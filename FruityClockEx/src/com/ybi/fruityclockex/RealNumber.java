package com.ybi.fruityclockex;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class RealNumber implements Number {

	private int numberValue;

	@Override
	public void draw(int xSize, int ySize, int position, Canvas c, Paint p) {
		Log.d(FruityClockActivity.TAG, "Drawing " + numberValue + " at position " + position + "/xSize=" + xSize + "/ySize=" + ySize);
		int pos = position * xSize / 8;

		switch (numberValue) {
		case 0:
			drawMatrix(c, p, pos, xSize, new int[] { 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1 });
			break;
		case 1:
			drawMatrix(c, p, pos, xSize, new int[] { 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0 });
			break;
		case 2:
			drawMatrix(c, p, pos, xSize, new int[] { 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1 });
			break;
		case 3:
			drawMatrix(c, p, pos, xSize, new int[] { 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1 });
			break;
		case 4:
			drawMatrix(c, p, pos, xSize, new int[] { 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1 });
			break;
		case 5:
			drawMatrix(c, p, pos, xSize, new int[] { 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1 });
			break;
		case 6:
			drawMatrix(c, p, pos, xSize, new int[] { 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1 });
			break;
		case 7:
			drawMatrix(c, p, pos, xSize, new int[] { 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 });
			break;
		case 8:
			drawMatrix(c, p, pos, xSize, new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 });
			break;
		case 9:
			drawMatrix(c, p, pos, xSize, new int[] { 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1 });
			break;
		default:
			break;
		}
	}

	private void drawMatrix(Canvas c, Paint paint, int pos, int xSize, int[] matrix) {
		int bsize = (xSize - 4) / 24;
		for (int i = 0; i < matrix.length; i++) {
			int x = i % 3;
			int y = i / 3;
			Log.d(FruityClockActivity.TAG, "x " + x);
			Log.d(FruityClockActivity.TAG, "y " + y);
			if (matrix[i] == 1) {
				c.drawRect(pos + x * bsize, y * bsize, pos + (x + 1) * bsize, (y + 1) * bsize, paint);
			}
		}
	}

	@Override
	public RealNumber set(int i) {
		numberValue = i;
		return this;
	}

}
