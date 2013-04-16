package com.ybi.fruityclock.utils;

public class RowData
{
	protected int mId;
	protected String mTitle;
	protected String mDetail;
	protected int mLocalImage;
	protected String mDistantImage;

	public RowData(int id, String title, String detail, Integer imgid)
	{
		mId = id;
		mTitle = title;
		mDetail = detail;
		mLocalImage = imgid;
	}

	public RowData(int id, String title, String detail, String imgid)
	{
		mId = id;
		mTitle = title;
		mDetail = detail;
		mDistantImage = imgid;
	}

	
	
	@Override
	public String toString()
	{
		return mId + " " + mTitle + " " + mDetail + " " + mLocalImage;
	}
}
