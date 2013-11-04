package com.ybi.android.fruitsclockex;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
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

	private static final String THEMES_LOCATION = "http://www.bnto.net/fruitsclockex/themes_hidden.json";
	private static final String ENCODING = "UTF-8";

	public static void downloadThemes(Context applicationContext) {

		HttpURLConnection urlConnection = null;
		String result = null;
		ArrayList<Theme> themes = null;

		// download the thing from the web
		try {
			URL url = new URL(THEMES_LOCATION);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDefaultUseCaches(false);
			urlConnection.setUseCaches(false);
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(false);   
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Pragma", "no-cache");
			urlConnection.setRequestProperty("Cache-Control", "no-cache");
			urlConnection.setRequestProperty("Expires", "-1");
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			result = convertStreamToString(in);

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
				Log.e(FruitsClockActivity.TAG, "Error === ", e);
			} catch (IOException e) {
				Log.e(FruitsClockActivity.TAG, "Error === ", e);
			}
		}

		// process the array insert only new things
		if (themes != null && !themes.isEmpty()) {
			for (Theme theme : themes) {
				theme.setTid(-1);
				Log.d(FruitsClockActivity.TAG, "Downloaded themes " + theme.getTitle());
				mergeTheme(theme);
			}
		}

		// check the installed themes and synchronize them if needed
		checkThemes(applicationContext);

	}

	private static void checkThemes(Context context) {
		Log.d(FruitsClockActivity.TAG, "Checking theme existence ");

		// check all theme even the available ones...
		int[] status = new int[] { Theme.STATUS_INSTALLED, Theme.STATUS_SELECTED, Theme.STATUS_AVAILABLE };

		// create the where part of the request
		StringBuilder statusList = new StringBuilder();
		for (int i = 0; i < status.length; i++) {
			statusList.append("status=" + status[i]);
			if (i < status.length - 1) {
				statusList.append(" OR ");
			}
		}

		// go get them please
		List<Entity> categories = DataFramework.getInstance().getEntityList("themes", statusList.toString());

		// now lets check them
		Iterator<Entity> iter = categories.iterator();
		while (iter.hasNext()) {
			Entity ent = iter.next();
			Theme theme = Theme.fromEntity(ent);
			if (theme.getLink() != null) {
				if (!themeInstalledOrNot(theme, context)) {
					// the theme has been kind of removed so lets say it's not installed
					theme.setStatus(Theme.STATUS_AVAILABLE);
					theme.toEntity().save();
				} else {
					// if the theme is available change it's status to installed
					theme.setStatus(Theme.STATUS_INSTALLED);
					theme.toEntity().save();
				}
			} else {
				// the theme has no lingk, get out of my database...
				// never happend
				theme.toEntity().delete();
			}
		}
	}

	private static void mergeTheme(Theme theme) {
		// check fEntityexistence
		Log.d(FruitsClockActivity.TAG, "Checking theme preexistence " + theme.getLink());
		List<Entity> previous = DataFramework.getInstance().getEntityList("themes", "link='" + theme.getLink() + "'");
		if (previous != null && previous.isEmpty()) {
			Log.d(FruitsClockActivity.TAG, "inserting theme " + theme.getTid());
			theme.toEntity().save();
		} else if (previous.size() == 1) {
			Theme otheme = Theme.fromEntity(previous.get(0));
			otheme.setDescription(theme.getDescription());
			otheme.setLink(theme.getLink());
			otheme.setLocation(theme.getLocation());
			otheme.setMediaContent(theme.getMediaContent());
			otheme.setMediaThumbnail(theme.getMediaThumbnail());
			otheme.setTitle(theme.getTitle());
			otheme.toEntity().save();
		}
	}

	private static String convertStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is, ENCODING).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static boolean themeInstalledOrNot(Theme theme, Context context) {
		PackageManager pm = context.getPackageManager();
		boolean app_installed = false;
		Log.d(FruitsClockActivity.TAG, "Checking for app " + theme.getLink());
		try {
			//pm.getPackageInfo(theme.getLink(), PackageManager.GET_ACTIVITIES);
			pm.getPackageInfo(theme.getLink(), PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}

		Log.d(FruitsClockActivity.TAG, "Checking for app " + theme.getLink() + " => " + app_installed);

		return app_installed;
	}
}
