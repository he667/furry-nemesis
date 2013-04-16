package com.ybi.fruityclock.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import com.ybi.fruityclock.FruityClock;

import android.util.Log;

public class Utils
{
	/**
	 * Telecharge un fichier depuis une url
	 * @param fileUrl
	 * @param fileName
	 */
	public static void downloadFromUrl(String fileUrl, String fileName)
	{
//		Log.d(FruityClock.TAG, "downloadFromUrl("+fileUrl+","+fileName+")");
		
		try
		{
			URL url = new URL(fileUrl);
			File file = new File(fileName);
//			long startTime = System.currentTimeMillis();
//			Log.d(FruityClock.TAG, "download begining");
//			Log.d(FruityClock.TAG, "download url:" + url);
//			Log.d(FruityClock.TAG, "downloaded file name:" + fileName);

			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1)
			{
				baf.append((byte) current);
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
//			Log.d(FruityClock.TAG, "download ready in"+ ((System.currentTimeMillis() - startTime) / 1000)+ " sec");
		} catch (IOException e)
		{
			Log.d(FruityClock.TAG, "Error: " + e);
		}

	}
	
	/**
	 * Check un file pour le last time qu'il was modified
	 * return true if file is uptodate
	 * return false if file is older than 24hrs ou doesnt exists 
	 * @param string
	 * @return 
	 */
	public static boolean isFileUpToDate(String string)
	{
		try
		{
//			Log.d(FruityClock.TAG, "File " + string);
			File file = new File(string);
			if (file.exists())
			{
				if ((System.currentTimeMillis() - file.lastModified()) < 3600*24*1000)
					return true;
				else
					return false;
			}
			else
				return false;

		} catch (Exception je)
		{
			Log.e(FruityClock.TAG, "Error w/file: " + je.getMessage());
		}		
		return false;
	}
}
