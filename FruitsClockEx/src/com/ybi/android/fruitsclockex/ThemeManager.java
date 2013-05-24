package com.ybi.android.fruitsclockex;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.dataframework.DataFramework;
import com.android.dataframework.Entity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ThemeManager {

	private static final String THEMES_LOCATION = "http://www.bnto.net/fruityclock/themes.json";
	private static final String ENCODING = "UTF-8";

	public static void downloadThemes(Context applicationContext) {

		HttpURLConnection urlConnection = null;
		String result = null;
		ArrayList<Theme> themes = null;

		// download the thing from the web
		try {
			URL url = new URL(THEMES_LOCATION);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			result = convertStreamToString(in);

			Log.d(FruitsClockActivity.TAG, "Downloaded themes " + result);

		} catch (IOException e) {
			Log.e(FruitsClockActivity.TAG, "Error IO Exception downloading theme", e);
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		// convert that json into an array
		if (result != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				themes = mapper.readValue(result, new TypeReference<ArrayList<Theme>>() {
				});

			} catch (JsonProcessingException e) {
				Log.d(FruitsClockActivity.TAG, "Error === ", e);
			} catch (IOException e) {
				Log.d(FruitsClockActivity.TAG, "Error === ", e);
			}
		}

		// process the array insert only new things
		if (themes != null && !themes.isEmpty()) {
			for (Theme theme : themes) {
				mergeTheme(theme);
			}
		}
	}

	private static void mergeTheme(Theme theme) {
		// check fEntityexistence
		Log.d(FruitsClockActivity.TAG, "Checking theme preexistence " + theme.getTid());
		List<Entity> previous = DataFramework.getInstance().getEntityList("themes", "tid=" + theme.getTid());
		if (previous != null && previous.isEmpty()) {
			Log.d(FruitsClockActivity.TAG, "inserting theme " + theme.getTid());
			theme.toEntity().save();
		}
	}

	private static String convertStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is, ENCODING).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static boolean themeInstalledOrNot(Theme theme, Context context) {
		PackageManager pm = context.getPackageManager();
		boolean app_installed = false;
		try {
			//pm.getPackageInfo(theme.getLink(), PackageManager.GET_ACTIVITIES);
			pm.getPackageInfo(theme.getLink(), PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}
}
