package com.ybi.android.fruitsclockex;

import com.android.dataframework.Entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties({"checksum"})
public class Theme implements Comparable<Theme> {
	//	final static SimpleDateFormat IN_FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
	//	final static SimpleDateFormat OUT_FORMATTER = new SimpleDateFormat("yyMMddHHmmssZ", Locale.US);
	public final static int STATUS_AVAILABLE = 1;
	public final static int STATUS_INSTALLED = 2;
	public final static int STATUS_SELECTED = 30;

	private long tid;
	private String title;
	private String link;
	private String description;
	private long date;
	private String mediaThumbnail;
	private String mediaContent;
	private String checksum;
	private String location;
	private int status;

	public Theme() {
		tid = -1;
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long id) {
		tid = id;
	}

	// getters and setters omitted for brevity
	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(long date) {
		this.date = date;
	}


	//	public String getDateForDatabase() {
	//		return OUT_FORMATTER.format(date);
	//	}
	//
	//	public String getDateForDisplay() {
	//		return IN_FORMATTER.format(date);
	//	}
	//
	//	public void setDateFromDatabase(String date) {
	//		// pad the date if necessary
	//		while (!date.endsWith("00")) {
	//			date += "0";
	//		}
	//		try {
	//			this.date = OUT_FORMATTER.parse(date.trim());
	//		} catch (java.text.ParseException e) {
	//			//Log.e(FruityClockActivity.TAG, "Parse Excpetion", e);
	//		}
	//	}
	//
	//	public void setDateFromFeed(String date) {
	//		// pad the date if necessary
	//		while (!date.endsWith("00")) {
	//			date += "0";
	//		}
	//		try {
	//			this.date = IN_FORMATTER.parse(date.trim());
	//		} catch (java.text.ParseException e) {
	//			//Log.e(FruityClockActivity.TAG, "Parse Excpetion", e);
	//		}
	//	}

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

	//	// sort by date
	@Override
	public int compareTo(Theme another) {
		if (another == null) {
			return 1;
		}
		// sort descending, most recent first
		return (int) (another.date - date);
	}

	public Entity toEntity() {
		Entity entity;
		if (tid > 0) {
			entity = new Entity("themes", tid);
		} else {
			entity = new Entity("themes");
		}

		entity.setValue("title", title);
		entity.setValue("link", link);
		entity.setValue("description", description);
		entity.setValue("date", date);
		entity.setValue("mediaThumbnail", mediaThumbnail);
		entity.setValue("mediaContent", mediaContent);
		entity.setValue("checksum", checksum);
		entity.setValue("location", location);
		entity.setValue("status", status);
		//entity.setValue("tid", tid);
		//entity.setForceId(tid);
		return entity;
	}

	public static Theme fromEntity(Entity ent) {
		Theme theme = new Theme();
		theme.setDate(ent.getValue("date") != null ? Long.parseLong((String) ent.getValue("date")) : 0);
		theme.setDescription((String) ent.getValue("description"));
		theme.setLink((String) ent.getValue("link"));
		theme.setTitle((String) ent.getValue("title"));
		theme.setMediaThumbnail((String) ent.getValue("mediaThumbnail"));
		theme.setMediaContent((String) ent.getValue("mediaContent"));
		theme.setLocation((String) ent.getValue("location"));
		//theme.setTid(ent.getValue("tid") != null ? Integer.parseInt((String) ent.getValue("tid")) : 0);
		theme.setTid(ent.getId());
		theme.setStatus(ent.getValue("status") != null ? Integer.parseInt((String) ent.getValue("status")) : 0);

		return theme;
	}

}
