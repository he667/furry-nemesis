package com.ybi.fruityclockex;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class FruityClockWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICK = "ACTION_CLICK";

	//	@Override
	//	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
	//
	//		// Get all ids
	//		ComponentName thisWidget = new ComponentName(context, FruityClockWidgetProvider.class);
	//		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
	//		for (int widgetId : allWidgetIds) {
	//			// Create some random data
	//			int number = new Random().nextInt(100);
	//
	//			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_fruity_clock_layout);
	//			// Set the text
	//			remoteViews.setTextViewText(R.id.text, String.valueOf(number));
	//
	//			// Register an onClickListener
	//			Intent intent = new Intent(context, FruityClockWidgetProvider.class);
	//
	//			intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	//			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
	//
	//			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	//			remoteViews.setOnClickPendingIntent(R.id.text, pendingIntent);
	//			appWidgetManager.updateAppWidget(widgetId, remoteViews);
	//		}
	//	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Toast.makeText(context, "TimeWidgetRemoved id(s):" + appWidgetIds, Toast.LENGTH_SHORT).show();
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		Toast.makeText(context, "onDisabled():last widget instance removed", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context, FruityClockBroadCastReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, FruityClockBroadCastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		//After after 3 seconds
		// next minute
		long nextMinute = (long) (System.currentTimeMillis() / 60000.0f + 1.0f) * 60000l;
		am.setRepeating(AlarmManager.RTC, nextMinute, 60000, pi);
		Log.d(FruityClockActivity.TAG, "--- onEnabled");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		ComponentName thisWidget = new ComponentName(context, FruityClockWidgetProvider.class);

		for (int widgetId : appWidgetManager.getAppWidgetIds(thisWidget)) {

			//Get the remote views
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_fruity_clock_layout);
			Calendar cal = Calendar.getInstance();
			remoteViews.setTextViewText(R.id.text, new SimpleDateFormat("hh:mm:ss", Locale.US).format(cal.getTime()));
			Log.d(FruityClockActivity.TAG, "--- onUpdate");
			// Set the text with the current time.
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
		//Do some operation here, once you see that the widget has change its size or position.
		Toast.makeText(context, "onAppWidgetOptionsChanged() called", Toast.LENGTH_SHORT).show();
	}
}