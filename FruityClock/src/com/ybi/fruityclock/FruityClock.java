package com.ybi.fruityclock;

import java.util.GregorianCalendar;

import com.ybi.fruityclock.activities.FruityClockActivity;
import com.ybi.fruityclock.bo.Theme;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RemoteViews;

// TODO 
public class FruityClock extends AppWidgetProvider
{

	public static final String URI_SCHEME = "ybi_widget";
	public static final String TAG = "YBI";

	private static final int MAX_WIDTH = 480;
	private static final int MAX_HEIGHT = 150;

	private static boolean isVisible = true;
	private static Context ctx;

	private static Theme currentTheme;

	public static void setVisible(boolean vis)
	{
		isVisible = vis;
	}

	public static void setCurrentTheme(Theme t)
	{
		currentTheme = t;
	}

	@Override
	public void onEnabled(Context context)
	{
		// This is only called once, regardless of the number of widgets of this type

		// recupere le widget id
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		ComponentName thisWidget = new ComponentName(context, FruityClock.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

		// donne les ids au service qui les donnera au broadcast
		FruityClockService.setWidgetIds(appWidgetIds);
		Intent svc = new Intent(context, FruityClockService.class);
		context.startService(svc);
		FruityClock.ctx = context;
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		try
		{
			FruityClock.ctx = context;
			final String action = intent.getAction();
			if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action))
			{
				final int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
				if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID)
				{
					this.onDeleted(context, new int[]
					{ appWidgetId });
				}
			} else if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action))
			{
				if (!URI_SCHEME.equals(intent.getScheme()))
				{
					final int[] appWidgetIds = intent.getExtras().getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);

					// donne les ids au service qui les donnera au broadcast
					FruityClockService.setWidgetIds(appWidgetIds);
					Intent svc = new Intent(context, FruityClockService.class);
					context.startService(svc);

				}
			}
		} catch (Exception e)
		{
			Log.e(FruityClock.TAG, "Probleme dans l'initialisation", e);
		}
		super.onReceive(context, intent);

	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		try
		{
			FruityClock.ctx = context;
			// restore the current theme
			if (currentTheme == null)
				currentTheme.loadFromSharedPreferences(context);
			
			// display the current widgets
			for (int appWidgetId : appWidgetIds)
			{
				Bitmap bitmap = drawWidget();

				RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
				views.setImageViewBitmap(R.id.ImageView01, bitmap);

				Intent intent = new Intent(context, FruityClockActivity.class);
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
				intent.setData(Uri.withAppendedPath(Uri.parse(URI_SCHEME
						+ "://widget/id/"), String.valueOf(appWidgetId)));
				PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);// PendingIntent.FLAG_UPDATE_CURRENT);

				views.setOnClickPendingIntent(R.id.ImageView01, pendingIntent);

				appWidgetManager.updateAppWidget(appWidgetId, views);
				// bitmap.recycle();

			}
		} catch (Exception e)
		{
			Log.e(FruityClock.TAG, "Probleme dans la mise a jour", e);
		}

	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		FruityClock.ctx = context;
		Log.d(FruityClock.TAG, "onDelete()");
		Intent svc = new Intent(context, FruityClockService.class);
		context.stopService(svc);
		super.onDeleted(context, appWidgetIds);
	}

	public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String titlePrefix)
	{
		FruityClock.ctx = context;
		if (isVisible)
		{
			Log.d(FruityClock.TAG, "updateAppWidget appWidgetId=" + appWidgetId
					+ " titlePrefix=" + titlePrefix);

			Bitmap bitmap = drawWidget();

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			views.setImageViewBitmap(R.id.ImageView01, bitmap);

			Intent intent = new Intent(context, FruityClockActivity.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			intent.setData(Uri.withAppendedPath(Uri.parse(URI_SCHEME
					+ "://widget/id/"), String.valueOf(appWidgetId)));
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);// PendingIntent.FLAG_UPDATE_CURRENT);

			views.setOnClickPendingIntent(R.id.ImageView01, pendingIntent);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		} else
		{
			Log.d(FruityClock.TAG, "WIDGET IS SLEEPING NOTHING TO BE DONE");
		}
		// bitmap.recycle();
	}

	public static Bitmap drawWidget()
	{
		// Log.d(FruityClock.TAG, "DRAWING WIDGET" );
		GregorianCalendar gc = new GregorianCalendar();

		int ijour = gc.get(GregorianCalendar.DATE) - 1;
		int isemaine = gc.get(GregorianCalendar.DAY_OF_WEEK) - 1;
		int imois = gc.get(GregorianCalendar.MONTH);

		int h = gc.get(GregorianCalendar.HOUR_OF_DAY);
		int m = gc.get(GregorianCalendar.MINUTE);

		Paint p = new Paint();
		p.setDither(false);
		p.setFilterBitmap(false);
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(8);
		p.setColor(0xFFFF0000);

		Bitmap bitmap = Bitmap.createBitmap((int) MAX_WIDTH, (int) MAX_HEIGHT, Config.ARGB_8888);
		bitmap.setDensity(DisplayMetrics.DENSITY_HIGH);
		Canvas canvas = new Canvas(bitmap);

		if ((currentTheme == null) || (currentTheme != null)
				&& (currentTheme.isDefault()))
		{
			Typeface clock = Typeface.createFromAsset(ctx.getAssets(), "alphatst.ttf");
			p.setTypeface(clock);
		} else
		{
			Log.d(FruityClock.TAG, "TYPEFACE = "
					+ currentTheme.getFontPath());
			Typeface clock = Typeface.createFromFile(currentTheme.getFontPath());
			p.setTypeface(clock);
		}

		p.setAntiAlias(true);
		p.setSubpixelText(true);
		p.setStyle(Paint.Style.FILL);
		p.setColor(Color.LTGRAY);
		p.setTextSize(20);

		// 57x112 et dp 33x112
		// (480 - (4x57+33)) / 2
		try
		{
			Bitmap bg = null;
			int lettre = 90;
			int dep = 23;
			int centre = 240;
			// centre = 0;
			centre = 480 - (2 * lettre + dep);
			int top = 1;

			int h1 = (int) h / 10;
			int h2 = h % 10;
			int m1 = (int) m / 10;
			int m2 = m % 10;

			// Log.d(FruityClock.TAG, "heeeere " + h1 + "-" + h2 + "-" + m1 + "-"+m2);
			if ((currentTheme == null) || (currentTheme != null)
					&& (currentTheme.isDefault()))
			{
				bg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.a0
						+ h1);
				canvas.drawBitmap(bg, centre - 2 * lettre - dep, top, p);

				bg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.a0
						+ h2);
				canvas.drawBitmap(bg, centre - lettre - dep, top, p);

				bg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.dp);
				canvas.drawBitmap(bg, centre - dep, top, p);

				bg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.a0
						+ m1);
				canvas.drawBitmap(bg, centre + dep, top, p);

				bg = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.a0
						+ m2);
				canvas.drawBitmap(bg, centre + lettre + dep, top, p);
			} else
			{
				bg = BitmapFactory.decodeFile(currentTheme.getPicturesPaths().get(h1));
				canvas.drawBitmap(bg, centre - 2 * lettre - dep, top, p);

				bg = BitmapFactory.decodeFile(currentTheme.getPicturesPaths().get(h2));
				canvas.drawBitmap(bg, centre - lettre - dep, top, p);

				bg = BitmapFactory.decodeFile(currentTheme.getPicturesPaths().get(10));
				canvas.drawBitmap(bg, centre - dep, top, p);

				bg = BitmapFactory.decodeFile(currentTheme.getPicturesPaths().get(m1));
				canvas.drawBitmap(bg, centre + dep, top, p);

				bg = BitmapFactory.decodeFile(currentTheme.getPicturesPaths().get(m2));
				canvas.drawBitmap(bg, centre + lettre + dep, top, p);
			}

			canvas.drawText(ctx.getResources().getStringArray(R.array.se)[isemaine], 10, 50, p);
			canvas.drawText(Integer.toString(ijour + 1), 10, 90, p);
			canvas.drawText(ctx.getResources().getStringArray(R.array.ms)[imois], 10, 130, p);

		} catch (Exception e)
		{
			Log.e(FruityClock.TAG, "io exception when looking for background", e);
		}

		return bitmap;
	}

}
