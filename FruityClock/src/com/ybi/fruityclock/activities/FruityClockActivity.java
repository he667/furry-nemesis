package com.ybi.fruityclock.activities;

import greendroid.app.GDListActivity;
import greendroid.widget.ItemAdapter;
import greendroid.widget.item.Item;
import greendroid.widget.item.ThumbnailItem;

import java.util.ArrayList;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.ybi.fruityclock.FruityClock;
import com.ybi.fruityclock.R;
import com.ybi.fruityclock.bo.Theme;

public class FruityClockActivity extends GDListActivity
{
	private final ArrayList<Item> data = new ArrayList<Item>();

	public static final String[] title = new String[]
	{ "Choose your Themes", "Download new Themes", "Settings" };
	public static final String[] detail = new String[]
	{ "Browse your theme library and select one or many theme",
			"Get new theme from Fruity Clock Web Site",
			"Life with options is fun" };
	public static final Integer[] imgid =
	{ R.drawable.ic_menu_tick, R.drawable.ic_menu_shopping,
			R.drawable.ic_menu_settings };

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTitle(R.string.app_name);

		for (int i = 0; i < title.length; i++)
		{
			data.add(new ThumbnailItem(title[i], detail[i], imgid[i]));
		}

		final ItemAdapter adapter = new ItemAdapter(this, data);
		setListAdapter(adapter);
		
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		Log.d(FruityClock.TAG, "MAIN ACTIVITY LIST ITEM CLICK" + position);
		switch (position)
		{
		case 0:
			Intent localThemeIntent = new Intent(this, FruityClockLocalThemeActivity.class);
			this.startActivity(localThemeIntent );
			break;
		case 1:
			Intent remoteThemeIntent = new Intent(this, FruityClockRemoteThemeActivity.class);
			this.startActivity(remoteThemeIntent );
			break;
		case 2:
			Intent settingsIntent = new Intent(this, FruityClockSettingsActivity.class);
			this.startActivity(settingsIntent );
			break;

		default: 
			break;
		}
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		Theme theme = new Theme();
		theme.loadFromSharedPreferences(this.getApplicationContext());
		FruityClock.setCurrentTheme(theme);

		int mAppWidgetId;
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null)
		{
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

			Log.i(FruityClock.TAG, "bouton ok pressed, try to pass back a value to the widget");
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
			FruityClock.updateAppWidget(this, appWidgetManager, mAppWidgetId, null);

			setResult(RESULT_OK, resultValue);
		}

		finish();
	}
	
	

}