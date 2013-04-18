package com.ybi.fruityclockex;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class FruityClockWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICK = "ACTION_CLICK";


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
			FruityClockWidgetRenderer.render(context, remoteViews);
			Log.d(FruityClockActivity.TAG, "--- onUpdate");
			// Set the text with the current time.
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}

	//	@Override
	//	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
	//		//Do some operation here, once you see that the widget has change its size or position.
	//		//Toast.makeText(context, "onAppWidgetOptionsChanged() called", Toast.LENGTH_SHORT).show();
	//		newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
	//		newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
	//		newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
	//		newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
	//		r.set(newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH), newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT), newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH), newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT));
	//	}
}