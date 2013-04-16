package com.ybi.fruityclock.activities;

import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.app.ListActivity;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ybi.fruityclock.FruityClock;
import com.ybi.fruityclock.R;
import com.ybi.fruityclock.bo.Theme;
import com.ybi.fruityclock.bo.ThemeManager;
import com.ybi.fruityclock.utils.CustomAdapter;
import com.ybi.fruityclock.utils.RowData;

public class FruityClockLocalThemeActivity extends ListActivity
{
	private Vector<RowData> data;
	private RowData rd;
	private final ArrayList<Theme> tl = new ArrayList<Theme>(); 
	
		@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		data = new Vector<RowData>();
	
		CustomAdapter adapter = new CustomAdapter(this, R.layout.themelist, data, (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE),1);
		setListAdapter(adapter);

		// charge les themes locaux
		Log.d(FruityClock.TAG, getExternalFilesDir(null).toString());
		ThemeManager.getLocalThemes(tl, getExternalFilesDir(null).getAbsolutePath());

		
		
		// initialise la liste des themes locaux
		for (int i = 0; i < tl.size(); i++)
		{
			try
			{
				if (tl.get(i).isDefault())
				rd = new RowData(i, tl.get(i).getName(), (String)tl.get(i).getDetail(), R.drawable.preview);
				else
				rd = new RowData(i, tl.get(i).getName(), (String)tl.get(i).getDetail(), tl.get(i).getPreview());
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
			data.add(rd);
		}


	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);

		// recupere tous les themes
		ThemeManager.getLocalThemes(tl, getExternalFilesDir(null).getAbsolutePath());
		
		// selectionne le theme
		Theme selectedTheme = tl.get(position);
		
		// sauvegarde le theme en tant que preference utilisateur
		selectedTheme.saveIntoSharedPreferences(v.getContext());
		

		// affiche un toast
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(v.getContext(), "Theme selected : " + selectedTheme.getName(), duration);
		toast.show();
		
		// ferme l'activité merci
		this.finish();
	}
}
