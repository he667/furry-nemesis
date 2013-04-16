package com.ybi.fruityclock.activities;

import greendroid.app.GDListActivity;
import greendroid.widget.ItemAdapter;
import greendroid.widget.item.Item;
import greendroid.widget.item.ThumbnailItem;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.ybi.fruityclock.FruityClock;
import com.ybi.fruityclock.R;
import com.ybi.fruityclock.utils.ExternalStorage;

public class FruityClockSettingsActivity extends GDListActivity
{
	private final ArrayList<Item> data = new ArrayList<Item>();;

	public static final String[] title = new String[]
	{ "Positions", "Tap Settings", "Colors", "Flush Cache", "About" };
	public static final String[] detail = new String[]
	{ "Choose text positions and alignements", "Set widget tapping response",
			"Life with colors is fun",
			"Remove theme and images files stored on your phone",
			"Why FruityClock?" };
	public static final Integer[] imgid =
	{ R.drawable.ic_menu_tick, R.drawable.ic_menu_shopping,
			R.drawable.ic_menu_settings, R.drawable.ic_menu_settings,
			R.drawable.ic_menu_settings };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.settings);
		/*
		data = new Vector<RowData>();
		for (int i = 0; i < title.length; i++)
		{
			try
			{
				rd = new RowData(i, title[i], detail[i], imgid[i]);
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
			data.add(rd);
		}

		CustomAdapter adapter = new CustomAdapter(this, R.layout.list, data, (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE), 0);
		setListAdapter(adapter);
		*/
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTitle(R.string.app_name);

		//Remove notification bar
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


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
		if (position == 0)
		{
			//"Positions"
			Intent aboutIntent = new Intent(this, FruityClockTestActivity.class);
			this.startActivity(aboutIntent);

			
		} else if (position == 1)
		{
			//"Tap Settings"
		} else if (position == 2)
		{
			//"Colors"
		} else if (position == 3)
		{
			//"Flush Cache"
			// supprime les repertoires de downloads et le fichier des themes
			if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			{
				File downloadDir = new File(android.os.Environment.getExternalStorageDirectory(), ExternalStorage.PATH
						+ "/downloads/");
				Log.d(FruityClock.TAG, "DELETING=" + downloadDir);
				
				if (downloadDir.exists())
				{
					deleteDirectory(downloadDir);
				}
			}
			
			// affiche un toast
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(v.getContext(), "Cache flushed!!!", duration);
			toast.show();
			
			
		} else if (position == 4)
		{
			//"About"
			Intent aboutIntent = new Intent(this, FruityClockAboutActivity.class);
			this.startActivity(aboutIntent);

		}


		// ferme l'activité merci
		//this.finish();
	}

	static public int deleteDirectory(File path) {
        int nbDeleted = 0;
        if (path.exists() && path.isDirectory()) {
            File[] files = path.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    nbDeleted += deleteDirectory(files[i]);
                } else {
                    if (files[i].delete()) {
                        nbDeleted++;
                    }

                }
            }
        } else if (path.exists()) {
            if (path.delete()) {
                nbDeleted++;
            }
        }
        return nbDeleted;
    }

}
