package com.ybi.fruityclockex;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.RemoteViews;

public class FruityClockBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		//Acquire the lock
		wl.acquire();

		//You can do the processing here update the widget/remote views.
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_fruity_clock_layout);
		Calendar cal = Calendar.getInstance();
		remoteViews.setTextViewText(R.id.text, new SimpleDateFormat("hh:mm:ss", Locale.US).format(cal.getTime()));

		ComponentName thiswidget = new ComponentName(context, FruityClockWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(thiswidget, remoteViews);
		//Release the lock
		wl.release();
	}
}