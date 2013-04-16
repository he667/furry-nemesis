package com.ybi.fruityclock.activities;

import java.util.ArrayList;
import java.util.Vector;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.ybi.fruityclock.FruityClock;
import com.ybi.fruityclock.R;
import com.ybi.fruityclock.bo.Theme;
import com.ybi.fruityclock.bo.ThemeManager;
import com.ybi.fruityclock.utils.LazyAdapter;
import com.ybi.fruityclock.utils.RowData;

public class FruityClockRemoteThemeActivity extends ListActivity
{

	private final ArrayList<Theme> tl = new ArrayList<Theme>();;
	private Vector<RowData> data;
	private RowData rd;
	private DownloadThemesListThread progressThemesListThread;
	private DownloadThemeThread progressThemeThread;
	private ProgressDialog dialogThemesList;
	private ProgressDialog dialogTheme;
	private LazyAdapter adapter; 
	
	
	final Handler handlerThemesList = new Handler()
	{
		public void handleMessage(Message msg)
		{
			dialogThemesList.dismiss();
			for (int i = 0; i < tl.size(); i++)
			{
				Log.d(FruityClock.TAG, "ADDING LINE =" + i + "--" + tl.get(i).getName() +" --");
				try
				{
					rd = new RowData(i, tl.get(i).getName(), (String)tl.get(i).getDetail(), tl.get(i).getPreview());
				} catch (ParseException e)
				{
					Log.e(FruityClock.TAG, "Parse Exception",e);
				}
				data.add(rd);
				
			}
			adapter.notifyDataSetChanged();
		}
	};

	final Handler handlerTheme = new Handler()
	{
		 public void handleMessage(Message msg) {
	            int total = msg.arg1;
	            dialogTheme.setProgress(total);
	            if (total >= 100){
	            	dialogTheme.dismiss();
	                progressThemeThread.setState(DownloadThemeThread.STATE_DONE);
	            }
	        }

	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		data = new Vector<RowData>();

		adapter = new LazyAdapter(this, tl);
		setListAdapter(adapter);
		
		Log.d(FruityClock.TAG, "HEYYYYYYYYYYYYY" + getExternalFilesDir(null).getAbsolutePath() );

	}

	@Override
	protected void onStart()
	{
		super.onStart();

		// attendez svp
		dialogThemesList = ProgressDialog.show(FruityClockRemoteThemeActivity.this, "", "Loading. Please wait...", true);

		progressThemesListThread = new DownloadThemesListThread(handlerThemesList);
		progressThemesListThread.start();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);

		Log.d(FruityClock.TAG, "CLICKED ON ITEM " + position);
		
		// recupere tous les themes
		if (tl != null)
		{
			// selectionne le theme
			Theme selectedTheme = tl.get(position);
			
			Log.d(FruityClock.TAG, "STARTING DOWNLOADING OF THEME " + selectedTheme.getName());
			
			//selectedTheme.loadRemote();
			dialogTheme = new ProgressDialog(FruityClockRemoteThemeActivity.this);
			dialogTheme.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialogTheme.setMessage("Loading...");
			dialogTheme.setCancelable(true);
			dialogTheme.show();

			progressThemeThread = new DownloadThemeThread(handlerTheme,selectedTheme );
			progressThemeThread.start();

			}

	}

	private class DownloadThemesListThread extends Thread
	{
		Handler mHandler;

		DownloadThemesListThread(Handler h)
		{
			mHandler = h;
		}

		public void run()
		{
			try
			{
				// charge les themes remote
//				ThemeManager.getRemoteThemes(tl, getExternalFilesDir(null).getAbsolutePath());

				Message msg = mHandler.obtainMessage();
				mHandler.sendMessage(msg);

			} catch (Exception e)
			{
				Log.e(FruityClock.TAG, "Thread Interrupted",e);
			}
		}

	}
	
	private class DownloadThemeThread extends Thread
	{
		int mState;
        int total;

		Handler mHandler;
		final static int STATE_DONE = 0;
		final static int STATE_RUNNING = 1;
		private Theme theme;

		DownloadThemeThread(Handler h, Theme t)
		{
			mHandler = h;
			theme = t;
		}

		public void run()
		{
			try
			{
				// charge le theme 
//				theme.loadRemote(getExternalFilesDir(null).getAbsolutePath());
//				Message msg = mHandler.obtainMessage();
//				msg.arg1 = 10 ;
//				mHandler.sendMessage(msg);
//			
//				// charge les images du theme 
//				total = 10 ;
//				// manque les deux points 
//				for (int i=0;i < 11; i++)
//				{
//					theme.loadRemotePictures(i);
//					msg = mHandler.obtainMessage();
//					total+=7;
//					msg.arg1 = total;
//					mHandler.sendMessage(msg);
//				}
//				
//				theme.loadRemoteFont();
//				msg = mHandler.obtainMessage();
//				msg.arg1 = 93;
//				mHandler.sendMessage(msg);
//				
//				theme.loadRemotePreview();
//				msg = mHandler.obtainMessage();
//				msg.arg1 = 100;
//				mHandler.sendMessage(msg);
//				
				


			} catch (Exception e)
			{
				Log.e(FruityClock.TAG, "Thread Interrupted",e);
			}
		}
		
		/* sets the current state for the thread,
         * used to stop the thread */
        public void setState(int state) {
            mState = state;
        }


	}

}
