package com.ybi.fruityclock.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.ybi.fruityclock.FruityClock;

import android.os.Environment;
import android.util.Log;

public class ExternalStorage
{
	public final static String PATH = "/data/data/com.ybi.fruityclock/";

	private boolean mExternalStorageAvailable = false;
	private boolean mExternalStorageWriteable = false;

	public boolean ismExternalStorageAvailable()
	{
		return mExternalStorageAvailable;
	}

	public void setmExternalStorageAvailable(boolean mExternalStorageAvailable)
	{
		this.mExternalStorageAvailable = mExternalStorageAvailable;
	}

	public boolean ismExternalStorageWriteable()
	{
		return mExternalStorageWriteable;
	}

	public void setmExternalStorageWriteable(boolean mExternalStorageWriteable)
	{
		this.mExternalStorageWriteable = mExternalStorageWriteable;
	}

	public ExternalStorage()
	{
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state))
		{
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
		{
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else
		{
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}

	public static ArrayList<String> getEntries(String externalFilesDir)
	{
		ArrayList<String> entries = new ArrayList<String>();

		File f = new File(externalFilesDir);
		if (f.canRead())
		{
			File[] lf = f.listFiles();
			if ((lf != null) && (lf.length > 0))
			{
				for (int i = 0; i < lf.length; i++)
				{
					Log.d(FruityClock.TAG, "Files = " + lf[i]);
					entries.add(lf[i].getAbsolutePath());
				}

			}
		}

		return entries;
	}

	public static String getFile(String filename) throws Exception
	{
		String line = null;
		StringBuilder records = new StringBuilder();

		// wrap a BufferedReader around FileReader
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

		// use the readLine method of the BufferedReader to read one line at a
		// time.
		// the readLine method returns null when there is nothing else to read.
		while ((line = bufferedReader.readLine()) != null)
		{
			records.append(line);
		}

		// close the BufferedReader when we're done
		bufferedReader.close();

		return records.toString();
	}

	public static void CopyStream(InputStream is, OutputStream os)
	{
		final int buffer_size = 1024;
		try
		{
			byte[] bytes = new byte[buffer_size];
			for (;;)
			{
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex)
		{
			Log.e(FruityClock.TAG, "CopyStream error" ,ex );
		}
	}
}
