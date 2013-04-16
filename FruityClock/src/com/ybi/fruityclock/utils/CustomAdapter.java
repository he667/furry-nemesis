package com.ybi.fruityclock.utils;

import java.util.List;

import com.ybi.fruityclock.FruityClock;
import com.ybi.fruityclock.R;
import com.ybi.fruityclock.R.id;
import com.ybi.fruityclock.R.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<RowData>
{
	private LayoutInflater mInflater;
	private int type;

	public CustomAdapter(Context context, int resource, List<RowData> objects, LayoutInflater inflater, int t)
	{
		super(context, resource, objects);
		this.mInflater = inflater;
		this.type = t;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		// TextView title = null;
		// TextView detail = null;
		// ImageView i11 = null;
		RowData rowData = getItem(position);
		if (null == convertView)
		{
			if (type == 0)
				convertView = mInflater.inflate(R.layout.list, null);
			else
				convertView = mInflater.inflate(R.layout.themelist, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else
		{
			// holder = new ViewHolder(convertView);
			holder = (ViewHolder) convertView.getTag();
		}

		holder = (ViewHolder) convertView.getTag();
		holder.gettitle().setText(rowData.mTitle);
		holder.getdetail().setText(rowData.mDetail);

		if (rowData.mLocalImage > 0)
		{
			holder.getImage().setImageResource(rowData.mLocalImage);
		} else
		{
			try
			{
				Bitmap bm;
				Log.d(FruityClock.TAG, "LOADING "
						+ rowData.mDistantImage);
				bm = BitmapFactory.decodeFile(rowData.mDistantImage);
				holder.getImage().setImageBitmap(bm);
			} catch (Exception e)
			{
				Log.d(FruityClock.TAG, "LOADING "
						+ rowData.mDistantImage);
				Log.e(FruityClock.TAG, "Wooow!!!", e);
			}
		}
		return convertView;
	}

	static class ViewHolder
	{
		private View mRow;
		private TextView title = null;
		private TextView detail = null;
		private ImageView i11 = null;

		public ViewHolder(View row)
		{
			mRow = row;
		}

		public TextView gettitle()
		{
			if (null == title)
			{
				title = (TextView) mRow.findViewById(R.id.title);
			}
			return title;
		}

		public TextView getdetail()
		{
			if (null == detail)
			{
				detail = (TextView) mRow.findViewById(R.id.detail);
			}
			return detail;
		}

		public ImageView getImage() 
		{
			if (null == i11)
			{
				i11 = (ImageView) mRow.findViewById(R.id.img);
			}
			return i11;
		}
	}
}
