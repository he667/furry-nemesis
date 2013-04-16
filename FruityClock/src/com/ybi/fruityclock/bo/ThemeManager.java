package com.ybi.fruityclock.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ybi.fruityclock.FruityClock;
import com.ybi.fruityclock.utils.ExternalStorage;
import com.ybi.fruityclock.utils.Utils;

import android.util.Log;

public class ThemeManager
{
	public static void getLocalThemes(final ArrayList<Theme> al, String externalFilesDir)
	{
		// il y a toujours un theme c'est le theme par defaut embarque par
		// l'appli
		// je n'ai besoin que de quoi constuire la previsualisation pour
		// l'instant
		Theme dt = new Theme();
		dt.setDefault(true);
		dt.setId(1);
		dt.setLoaded(true);
		dt.setName("Fruity Default");
		al.add(dt);

		// ensuite il y a les autres
		ExternalStorage es = new ExternalStorage();
		if (es.ismExternalStorageAvailable())
		{
			ArrayList<String> listEntries = ExternalStorage.getEntries(externalFilesDir);
			if ((listEntries != null) && (listEntries.size() > 0))
			{
				for (Iterator<String> iterator = listEntries.iterator(); iterator.hasNext();)
				{
					// recupre l'identifiant du theme
					String ik = (String) iterator.next();
					String tId = ik.substring(externalFilesDir.length() + 1);
					try
					{
						int iik = Integer.parseInt(tId);
						if (iik != 1)
						{
							Theme localTheme = new Theme();
							localTheme.loadLocal(ik);
							// if (localTheme.isLoaded())
							al.add(localTheme);
						}
					} catch (NumberFormatException e)
					{

					}
				}
			}
		}
	}
	
//	public static void getRemoteThemes(final ArrayList<Theme> al, String externalFilesDir)
//	{
//		File downloadDir = null;
//
//		// verifie que le repertoire des telechargements existe
//		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
//		{
//			downloadDir = new File(externalFilesDir + "/downloads/");
//
//			if (!downloadDir.exists())
//				downloadDir.mkdirs();
//
//			// validite de la liste :24h, parce que je suis une feignasse et que
//			// 1theme/j c'est enorme (nan je deconne)
//			// regarde si le fichier des themes est deja la
//			// verifie si le fichier existe
//			if (!Utils.isFileUpToDate(downloadDir + "/themes.json"))
//			{
//				// recupere le fichier des themes et sauvegarde le pour usage
//				// ulterieur
//				Utils.downloadFromUrl(FruityClock.THEME_REPO + "themes.json", downloadDir
//						+ "/themes.json");
//			}
//
//			// ouvre le,
//			// dejsonifie le
//			// et transforme tout ca en liste
//			// renvoie la liste
//			try
//			{
//				String jsontext = ExternalStorage.getFile(downloadDir
//						+ "/themes.json");
//				JSONArray post = new JSONArray(jsontext);
//
//				for (int i = 0; i < post.length(); i++)
//				{
//					JSONObject obj = (JSONObject) post.get(i);
//					int id = obj.getInt("id");
//					String name = obj.getString("name");
//					String preview = obj.getString("preview");
//
//					// cree un theme de previsualisation
//					Theme lt = new Theme();
//					lt.setId(id);
//					lt.setName(name);
//					lt.setPreview(FruityClock.THEME_REPO + id + "/" + preview);
//
//					// ajoute a la liste
//					al.add(lt);
//				}
//			} catch (JSONException e)
//			{
//				Log.e(FruityClock.TAG, "Error json w/file: ", e);
//
//			} catch (Exception e)
//			{
//				Log.e(FruityClock.TAG, "Error w/file: ", e);
//
//			}
//		}
//	}
}
