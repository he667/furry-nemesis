package com.ybi.fruityclock.bo;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.ybi.fruityclock.FruityClock;
import com.ybi.fruityclock.utils.ExternalStorage;
import com.ybi.fruityclock.utils.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Theme
{
	// le nom des preferences pour la sauvegarde
	protected final static String PREFS_NAME = "com.ybi.fruityclock";

	private int id;
	private String name;
	private String preview;
	private String themePath;
	private ArrayList<String> picturesPaths;
	private ArrayList<String> picturesUrl;
	private String fontPath;
	private String fontUrl;
	private boolean isLoaded;
	private boolean isDefault;
	private boolean isSelected;
	private String author;
	private String category;
	private int size;

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPreview()
	{
		return preview;
	}

	public void setPreview(String preview)
	{
		this.preview = preview;
	}

	public String getThemePath()
	{
		return themePath;
	}

	public void setThemePath(String path)
	{
		this.themePath = path;
	}

	public ArrayList<String> getPicturesPaths()
	{
		return picturesPaths;
	}

	public void setPicturesPaths(ArrayList<String> picturesPaths)
	{
		this.picturesPaths = picturesPaths;
	}

	public ArrayList<String> getPicturesUrl()
	{
		return picturesUrl;
	}

	public void setPicturesUrl(ArrayList<String> picturesUrl)
	{
		this.picturesUrl = picturesUrl;
	}

	public String getFontPath()
	{
		return fontPath;
	}

	public void setFontPath(String fontPath)
	{
		this.fontPath = fontPath;
	}

	public String getFontUrl()
	{
		return fontUrl;
	}

	public void setFontUrl(String fontUrl)
	{
		this.fontUrl = fontUrl;
	}

	public boolean isLoaded()
	{
		return isLoaded;
	}

	public void setLoaded(boolean isLoaded)
	{
		this.isLoaded = isLoaded;
	}

	public boolean isDefault()
	{
		return isDefault;
	}

	public void setDefault(boolean isDefault)
	{
		this.isDefault = isDefault;
	}

	public boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}

	public Theme()
	{
		isLoaded = false;
		isDefault = false;
		isSelected = false;
		picturesPaths = new ArrayList<String>();
	}

	public void loadLocal(String pid)
	{
		// recupere un theme afin de le previsualiser
		// on ne recupere donc qu'une seule chose, le nom, les details et
		// l'image de preview
		try
		{
			String jsontext = ExternalStorage.getFile(pid + "/" + "theme.json");
			JSONObject post = new JSONObject(jsontext);

			this.themePath = pid;
			this.id = post.getInt("id");
			this.name = post.getString("name");
			this.preview = pid + "/" + post.getString("preview");
			this.author = post.getString("author");
			this.size = post.getInt("size");
			this.category = post.getString("category");
			
			for (int i = 0; i < 10; i++)
			{
				this.picturesPaths.add(pid + "/" + post.getString("a" + i));
			}
			this.picturesPaths.add(pid + "/" + post.getString("adp"));
			this.fontPath = pid + "/" + post.getString("ttf");

			// displayTheme();

		} catch (Exception je)
		{
			Log.e(FruityClock.TAG, "Error w/file: " + je.getMessage());
		}

	}

	

	public void saveIntoSharedPreferences(Context context)
	{
		if (!this.isDefault)
		{

			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();

			editor.putInt("id", this.id);
			editor.putString("name", this.name);
			editor.putString("preview", this.preview);
			editor.putString("themePath", this.themePath);
			editor.putInt("size", this.size);
			editor.putString("category", this.category);
			editor.putString("author", this.author);

			for (int i = 0; i < 10; i++)
				editor.putString("a" + i, this.picturesPaths.get(i));

			editor.putString("adp", this.picturesPaths.get(10));
			editor.putString("fontPath", this.fontPath);

			editor.commit();
		} else
		{
			SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt("id", this.id);
			editor.commit();
		}

	}

	public void loadFromSharedPreferences(Context context)
	{
		SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);

		this.id = settings.getInt("id", 1);
		if (this.id != 1)
		{
			this.name = settings.getString("name", "Fruity Default");
			this.themePath = settings.getString("themePath", "");
			this.preview = settings.getString("preview", "");
			this.author= settings.getString("author", "");
			this.size= settings.getInt("size", 0);
			this.category = settings.getString("category", "");

			picturesPaths = new ArrayList<String>();

			for (int i = 0; i < 10; i++)
				this.picturesPaths.add(settings.getString("a" + i, ""));
			this.picturesPaths.add(settings.getString("adp", ""));
			this.fontPath = settings.getString("fontPath", "");
		} else
		{
			this.setDefault(true);
			this.setId(1);
			this.setLoaded(true);
			this.setName("Fruity Default");
			this.setPreview("/1/kh.png");
		}
		// displayTheme();
	}

	public void displayTheme()
	{
		if (isDefault)
		{
			Log.d(FruityClock.TAG, "DEFAULT THEME SELECTED");
		} else
		{
			Log.d(FruityClock.TAG, "DISPLAYING THEME : ");
			Log.d(FruityClock.TAG, "  > id=" + id);
			Log.d(FruityClock.TAG, "  > name=" + name);
			Log.d(FruityClock.TAG, "  > themePath=" + themePath);
			Log.d(FruityClock.TAG, "  > preview=" + preview);
			Log.d(FruityClock.TAG, "  > author=" + author);
			Log.d(FruityClock.TAG, "  > category=" + category);
			Log.d(FruityClock.TAG, "  > size=" + size);
			for (int i = 0; i < 10; i++)
				Log.d(FruityClock.TAG, "  > picturesPaths[" + i + "]="
						+ picturesPaths.get(i));
			Log.d(FruityClock.TAG, "  > picturesPaths[adp]="
					+ picturesPaths.get(10));
			Log.d(FruityClock.TAG, "  > fontPath=" + fontPath);
		}
	}

	

//	public void loadRemote(String externalFilesDir)
//	{
//		// recupere le fichier themes sur le serveur qui va bien
//		File downloadDir = null;
//
//		// verifie que le repertoire des telechargements existe
//		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
//		{
//			downloadDir = new File(externalFilesDir + "/" + this.id + "/");
//
//			Log.d(FruityClock.TAG, "DOWNLOADDIR=" + downloadDir);
//
//			if (!downloadDir.exists())
//				downloadDir.mkdirs();
//
//			// telecharge le fichier theme
//			Utils.downloadFromUrl(FruityClock.THEME_REPO + this.id
//					+ "/theme.json", downloadDir + "/theme.json");
//
//			// ouvre le,
//			// dejsonifie le
//			// et transforme tout ca en liste
//			// renvoie la liste
//			try
//			{
//				String jsontext = ExternalStorage.getFile(downloadDir
//						+ "/theme.json");
//				JSONObject post = new JSONObject(jsontext);
//
//				String pid = downloadDir.getAbsolutePath();
//				this.themePath = pid;
//				this.id = post.getInt("id");
//				this.name = post.getString("name");
//				this.preview = pid + "/" + post.getString("preview");
//				this.size = post.getInt("size");
//				this.author = post.getString("author");
//				this.category = pid + "/" + post.getString("category");
//
//				for (int i = 0; i < 10; i++)
//				{
//					this.picturesPaths.add(post.getString("a" + i));
//				}
//				this.picturesPaths.add(post.getString("adp"));
//				this.fontPath = post.getString("ttf");
//
//				// displayTheme();
//
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

//	public void loadRemotePictures(int i)
//	{
//		Utils.downloadFromUrl(FruityClock.THEME_REPO + this.id + "/" + this.picturesPaths.get(i), this.themePath + "/" + this.picturesPaths.get(i));
//	}
//
//	public void loadRemoteFont()
//	{
//		Utils.downloadFromUrl(FruityClock.THEME_REPO + this.id + "/"+ this.fontPath, this.themePath + "/" + this.fontPath);
//	}
//
//	public void loadRemotePreview()
//	{
//		Utils.downloadFromUrl(FruityClock.THEME_REPO + this.id + "/preview.png", this.themePath + "/preview.png");
//	}

	public CharSequence getDetail()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Author:");
		if (this.author != null)
			sb.append(this.author);
		else
			sb.append("Anonymous");
		sb.append(" - Category:");
		if (this.author != null)
			sb.append(this.category);
		else
			sb.append("Unknown");
		sb.append(" - Size:");
		if (this.author != null)
			sb.append(Integer.toString(this.size));
		else
			sb.append("000");
		sb.append("k");
		return sb.toString();
	}

}
