package com.ybi.fruityclockex;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class SquareNumber implements Number {

	private int numberValue;

	@Override
	public void draw(int xSize, int ySize, int position, Canvas c, Paint p) {
		Log.d(FruityClockActivity.TAG, "Drawing " + numberValue + " at position " + position + "/xSize=" + xSize + "/ySize=" + ySize);
		int pos = position * xSize / 8;
		int smallsize = xSize / 16 - 4;
		int bigsize = xSize / 8 - 4;

		p.setAlpha(50);
		drawCircle(c, pos, ySize / 2, xSize / 8, p);
		p.setAlpha(200);
		switch (numberValue) {
		case 0:
			p.setAlpha(100);
			drawCircle(c, pos, ySize / 4, smallsize, p);
			break;
		case 1:
			drawCircle(c, pos, ySize / 4, smallsize, p);
			break;
		case 2:
			drawCircle(c, pos - xSize / 16, ySize / 4, smallsize, p);
			drawCircle(c, pos + xSize / 16, ySize / 4, smallsize, p);
			break;
		case 3:
			drawCircle(c, pos - xSize / 16, ySize / 4, smallsize, p);
			drawCircle(c, pos + xSize / 16, ySize / 4, smallsize, p);
			drawCircle(c, pos, 2 * ySize / 4, smallsize, p);
			break;
		case 4:
			drawCircle(c, pos - xSize / 16, ySize / 4, smallsize, p);
			drawCircle(c, pos + xSize / 16, ySize / 4, smallsize, p);
			drawCircle(c, pos - xSize / 16, 2 * ySize / 4, smallsize, p);
			drawCircle(c, pos + xSize / 16, 2 * ySize / 4, smallsize, p);
			break;
		case 5:
			drawCircle(c, pos, ySize / 4, bigsize, p);
			break;
		case 6:
			drawCircle(c, pos, ySize / 4, bigsize, p);
			drawCircle(c, pos, 2 * ySize / 4, smallsize, p);
			break;
		case 7:
			drawCircle(c, pos, ySize / 4, bigsize, p);
			drawCircle(c, pos - xSize / 16, 2 * ySize / 4, smallsize, p);
			drawCircle(c, pos + xSize / 16, 2 * ySize / 4, smallsize, p);
			break;
		case 8:
			drawCircle(c, pos, ySize / 4, bigsize, p);
			drawCircle(c, pos - xSize / 16, 2 * ySize / 4, smallsize, p);
			drawCircle(c, pos + xSize / 16, 2 * ySize / 4, smallsize, p);
			drawCircle(c, pos, 3 * ySize / 4, smallsize, p);
			break;
		case 9:
			drawCircle(c, pos, ySize / 4, bigsize, p);
			drawCircle(c, pos - xSize / 16, 2 * ySize / 4, smallsize, p);
			drawCircle(c, pos + xSize / 16, 2 * ySize / 4, smallsize, p);
			drawCircle(c, pos - xSize / 16, 3 * ySize / 4, smallsize, p);
			drawCircle(c, pos + xSize / 16, 3 * ySize / 4, smallsize, p);
			break;
		default:
			break;
		}
	}

	private void drawCircle(Canvas c, int i, int j, int smallsize, Paint p) {
		c.drawRect(i - smallsize, j - smallsize, i + smallsize, j + smallsize, p);

	}

	@Override
	public SquareNumber set(int i) {
		numberValue = i;
		return this;
	}

}
