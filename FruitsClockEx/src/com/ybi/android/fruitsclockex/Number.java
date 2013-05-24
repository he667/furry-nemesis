package com.ybi.android.fruitsclockex;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Number {

	void draw(int xSize, int ySize, int position, Canvas c, Paint p);

	Number set(int i);
}
