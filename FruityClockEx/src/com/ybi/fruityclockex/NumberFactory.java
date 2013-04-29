package com.ybi.fruityclockex;

import android.content.Context;

public class NumberFactory {

	/** Donnée de classe contenant l'instance courante */
	private static NumberFactory instance = new NumberFactory();

	public static NumberFactory getInstance(Context context) {
		return instance;
	}

	public Number getNumber() {
		int type = 0;
		switch (type) {
		case 2:
			return new SquareNumber();
		case 1:
			return new RoundNumber();
		case 3:
			return new RealNumber();
		default:
			return new FlippingNumber();
		}
	}

}
