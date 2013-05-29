package com.ybi.android.fruitsclockex;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class NumberFactory {

	public static final String PREFS_FILE = "theme.preferences";
	public static final String PREFS_TITLE = "theme.title";
	public static final String PREFS_LINK = "theme.link";
	private static final NumberFactory instance = new NumberFactory();

	private String themeLink;
	private String themeTitle;
	private Context context;

	public static NumberFactory getInstance(Context context) {

		// get me the selected theme please
		final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
		String link = prefs.getString(PREFS_LINK, null);
		String title = prefs.getString(PREFS_TITLE, null);
		Log.d(FruitsClockActivity.TAG, "Theme in sharedprfs link = " + link);
		Log.d(FruitsClockActivity.TAG, "Theme in sharedprfs title = " + title);
		if (link != null && title != null) {
			// this is what im looking for
			instance.setThemeLink(link);
			instance.setThemeTitle(title);
			instance.setContext(context);
		}

		return instance;
	}

	public Number getNumber() {
		//		int type = 0;
		//		switch (type) {
		//		case 2:
		//			return new SquareNumber();
		//		case 1:
		//			return new RoundNumber();
		//		case 3:
		//			return new RealNumber();
		//		default:
		//			return new FlippingNumber();
		//		}
		if (themeLink != null) {
			return new ThemeNumber(context, themeLink);
		} else {
			return new RealNumber();
		}
	}

	public void setThemeLink(String themeLink) {
		this.themeLink = themeLink;
	}

	public void setThemeTitle(String themeTitle) {
		this.themeTitle = themeTitle;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
