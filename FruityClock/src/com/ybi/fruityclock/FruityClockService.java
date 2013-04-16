package com.ybi.fruityclock;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class FruityClockService extends Service
{
	public static final String TAG = FruityClock.TAG;
	public static int[] appWidgetIds;
	private static FruityClockBroadcastReceiver acb = null;
	
	
	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
		this.setForeground(true);
		Log.v(TAG, "DemoService.onStart()");
		if (acb ==null)
		{
			acb = new FruityClockBroadcastReceiver();
		}
		else
		{
			try
			{
				unregisterReceiver(acb);
			} catch (Exception e)
			{
				Log.w(FruityClock.TAG, "failed to unregister previous receiver... Maybe this is the first time or am did not destroy it completely...");
			}
		}
		IntentFilter inf = new IntentFilter();
		inf.addAction(Intent.ACTION_TIME_TICK);
		inf.addAction(Intent.ACTION_SCREEN_OFF);
		inf.addAction(Intent.ACTION_SCREEN_ON);
		inf.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(acb, inf);
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		this.setForeground(true);
		Log.v(TAG, "DemoService.onStart()");
		if (acb ==null)
		{
			acb = new FruityClockBroadcastReceiver();
		}
		else
		{
			try
			{
				unregisterReceiver(acb);
			} catch (Exception e)
			{
				Log.w(FruityClock.TAG, "failed to unregister previous receiver... Maybe this is the first time or am did not destroy it completely...");
			}
		}
		IntentFilter inf = new IntentFilter();
		inf.addAction(Intent.ACTION_TIME_TICK);
		inf.addAction(Intent.ACTION_SCREEN_OFF);
		inf.addAction(Intent.ACTION_SCREEN_ON);
		inf.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(acb, inf);
	}

	public static void setWidgetIds(int[] tAppWidgetIds)
	{
		appWidgetIds = tAppWidgetIds;

	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (acb!=null)
		{
		unregisterReceiver(acb);
		}
		Log.v(TAG, "DemoService.onDestroy()");
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
