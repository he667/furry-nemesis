package com.ybi.fruityclockex;

import android.graphics.Canvas;
import android.graphics.Paint;

public class RoundNumber implements Number {

	private int numberValue;

	@Override
	public void draw(int xSize, int ySize, int position, Canvas c, Paint p) {
		int pos = position * xSize / 8;
		int smallsize = xSize / 24 - 4;
		int bigsize = xSize / 10 - 4;

		for (int i = 1; i < 3; i++) {

			if (i == 1) {
				p.setAlpha(200);
			} else {
				p.setAlpha(40);
			}
			switch (numberValue) {
			case 0:
				p.setAlpha(100 / i);
				c.drawCircle(pos, ySize / 4, smallsize, p);
				break;
			case 1:
				c.drawCircle(pos, ySize / 4, smallsize, p);
				break;
			case 2:
				c.drawCircle(pos - xSize / 16, ySize / 4, smallsize, p);
				c.drawCircle(pos + xSize / 16, ySize / 4, smallsize, p);
				break;
			case 3:
				c.drawCircle(pos - xSize / 16, ySize / 4, smallsize, p);
				c.drawCircle(pos + xSize / 16, ySize / 4, smallsize, p);
				c.drawCircle(pos, 2 * ySize / 4, smallsize, p);
				break;
			case 4:
				c.drawCircle(pos - xSize / 16, ySize / 4, smallsize, p);
				c.drawCircle(pos + xSize / 16, ySize / 4, smallsize, p);
				c.drawCircle(pos - xSize / 16, 2 * ySize / 4, smallsize, p);
				c.drawCircle(pos + xSize / 16, 2 * ySize / 4, smallsize, p);
				break;
			case 5:
				c.drawCircle(pos, ySize / 4, bigsize, p);
				break;
			case 6:
				c.drawCircle(pos, ySize / 4, bigsize, p);
				c.drawCircle(pos, 2 * ySize / 4, smallsize, p);
				break;
			case 7:
				c.drawCircle(pos, ySize / 4, bigsize, p);
				c.drawCircle(pos - xSize / 16, 2 * ySize / 4, smallsize, p);
				c.drawCircle(pos + xSize / 16, 2 * ySize / 4, smallsize, p);
				break;
			case 8:
				c.drawCircle(pos, ySize / 4, bigsize, p);
				c.drawCircle(pos - xSize / 16, 2 * ySize / 4, smallsize, p);
				c.drawCircle(pos + xSize / 16, 2 * ySize / 4, smallsize, p);
				c.drawCircle(pos, 3 * ySize / 4, smallsize, p);
				break;
			case 9:
				c.drawCircle(pos, ySize / 4, bigsize, p);
				c.drawCircle(pos - xSize / 16, 2 * ySize / 4, smallsize, p);
				c.drawCircle(pos + xSize / 16, 2 * ySize / 4, smallsize, p);
				c.drawCircle(pos - xSize / 16, 3 * ySize / 4, smallsize, p);
				c.drawCircle(pos + xSize / 16, 3 * ySize / 4, smallsize, p);
				break;
			default:
				break;
			}
			smallsize = xSize / 24;
			bigsize = xSize / 10;
		}
	}

	@Override
	public RoundNumber set(int i) {
		numberValue = i;
		return this;
	}

}
