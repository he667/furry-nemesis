package com.ybi.fruityclock;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class FruityClockBroadcastReceiver extends BroadcastReceiver
{


	@Override
	public void onReceive(Context context, Intent intent)
	{
		Log.d(FruityClock.TAG, "RECEIVED SOMETHING");
		if (intent.getAction().equals(Intent.ACTION_TIME_TICK)
				|| intent.getAction().equals(Intent.ACTION_TIME_CHANGED))
		{
			Log.d(FruityClock.TAG, "RECEIVED TIME TICK!!!!!!!!");
			tick(context);

		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
		{
			FruityClock.setVisible (false);
		} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
		{
			FruityClock.setVisible (true);
			tick(context);
		}
//		else if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED))
//		{
//			int rawlevel = intent.getIntExtra("level", -1);
//            int scale = intent.getIntExtra("scale", -1);
//            int level = -1;
//            if (rawlevel >= 0 && scale > 0) {
//                level = (rawlevel * 100) / scale;
//            }
//            FruityClock.setBatteryLevel (level);
//			tick(context);
//		}

	}

	private void tick(Context context)
	{
		try
		{
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			ComponentName thisWidget = new ComponentName(context, FruityClock.class);
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

			if ((appWidgetIds != null) && (appWidgetIds.length > 0))
				for (int appWidgetId : appWidgetIds)
				{
					Log.i(FruityClock.TAG, "updating widget from time tick");
					// AppWidgetManager appWidgetManager =
					// AppWidgetManager.getInstance(context);
					FruityClock.updateAppWidget(context, appWidgetManager, appWidgetId, null);
				}
		} catch (Exception e)
		{
			Log.e(FruityClock.TAG, "Something wrong with the launch of the widget update from the broadcast", e);
		}
	}
};