package com.ybi.fruityclockex;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.android.dataframework.Entity;

import android.util.Log;

public class Theme implements Comparable<Theme> {
	final static SimpleDateFormat IN_FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
	final static SimpleDateFormat OUT_FORMATTER = new SimpleDateFormat("yyMMddHHmmssZ", Locale.US);

	private long id;
	private String title;
	private URL link;
	private String description;
	private Date date;
	private String mediaThumbnail;
	private String mediaContent;
	private String checksum;
	private String location;
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// getters and setters omitted for brevity
	public void setLink(String link) {
		try {
			this.link = new URL(link);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public URL getLink() {
		return link;
	}

	public void setLink(URL link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateForDatabase() {
		return OUT_FORMATTER.format(date);
	}

	public String getDateForDisplay() {
		return IN_FORMATTER.format(date);
	}

	public void setDateFromDatabase(String date) {
		// pad the date if necessary
		while (!date.endsWith("00")) {
			date += "0";
		}
		try {
			this.date = OUT_FORMATTER.parse(date.trim());
		} catch (java.text.ParseException e) {
			Log.e(FruityClockActivity.TAG, "Parse Excpetion", e);
		}
	}

	public void setDateFromFeed(String date) {
		// pad the date if necessary
		while (!date.endsWith("00")) {
			date += "0";
		}
		try {
			this.date = IN_FORMATTER.parse(date.trim());
		} catch (java.text.ParseException e) {
			Log.e(FruityClockActivity.TAG, "Parse Excpetion", e);
		}
	}

	public void setMediaContent(String mediaContent) {
		this.mediaContent = mediaContent;
	}

	public void setMediaThumbnail(String mediaThumbnail) {
		this.mediaThumbnail = mediaThumbnail;
	}

	public String getMediaContent() {
		return mediaContent;
	}

	public String getMediaThumbnail() {
		return mediaThumbnail;
	}

	public String getChecksum() {
		return Utils.md5(link.toString());
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return title + " / " + description + " / " + link.toString();
	}

	// sort by date
	@Override
	public int compareTo(Theme another) {
		if (another == null) {
			return 1;
		}
		// sort descending, most recent first
		return another.date.compareTo(date);
	}

	public Theme copy() {
		Theme theme = new Theme();
		theme.setDate(date);
		theme.setDescription(description);
		theme.setLink(link);
		theme.setTitle(title);
		theme.setMediaThumbnail(mediaThumbnail);
		theme.setMediaContent(mediaContent);
		theme.setLocation(location);
		theme.setStatus(status);
		return theme;
	}

	public Entity toEntity() {
		Entity entity = new Entity("themes");
		entity.setValue("title", title);
		entity.setValue("link", link);
		entity.setValue("description", description);
		entity.setValue("date", date);
		entity.setValue("mediaThumbnail", mediaThumbnail);
		entity.setValue("mediaContent", mediaContent);
		entity.setValue("checksum", checksum);
		entity.setValue("location", location);
		entity.setValue("status", status);
		return entity;
	}

	public static Theme fromEntity(Entity ent) {
		Theme theme = new Theme();
		theme.setDateFromDatabase((String) ent.getValue("date"));
		theme.setDescription((String) ent.getValue("descritption"));
		theme.setLink((String) ent.getValue("link"));
		theme.setTitle((String) ent.getValue("title"));
		theme.setMediaThumbnail((String) ent.getValue("mediaThumbnail"));
		theme.setMediaContent((String) ent.getValue("mediaContent"));
		theme.setLocation((String) ent.getValue("location"));
		theme.setStatus(ent.getValue("status") != null ? (Integer) ent.getValue("status") : 0);
		return theme;
	}

}
