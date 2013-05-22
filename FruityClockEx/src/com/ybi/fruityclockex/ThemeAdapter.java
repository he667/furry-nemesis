package com.ybi.fruityclockex;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ThemeAdapter extends BaseAdapter {

	private final List<Theme> items;
	private final Context context;

	private boolean mNotifyOnChange;
	private int maxSeenIndex;

	// Now handle the main ImageView thumbnails
	static ViewHolder holder;

	private final OnClickListener onClickListener;

	public ThemeAdapter(Context context, List<Theme> listItems) {
		this.context = context;
		items = listItems;
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(FruityClockActivity.TAG, "Clicked " + v.getTag());
				Toast.makeText(ThemeAdapter.this.context, "azdazdazda", Toast.LENGTH_SHORT).show();
			}
		};

	}

	public void clear() {
		items.clear();
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, final View convertView, final ViewGroup arg2) {
		final Theme item = (Theme) getItem(position);

		View localView = convertView;

		ImageView iv = null;
		TextView tv = null;
		TextView cv = null;

		if (convertView == null) { // if it's not recycled, instantiate and initialize

			// i need a more complex view please
			//Inflate the layout
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			localView = li.inflate(R.layout.list_element_centered, null);

			// Add The Text!!!
			tv = (TextView) localView.findViewById(R.id.textView1);
			cv = (TextView) localView.findViewById(R.id.textView2);
			Typeface someFont = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntul.ttf");

			tv.setTypeface(someFont);
			cv.setTypeface(someFont);

			// Add The Image!!!
			iv = (ImageView) localView.findViewById(R.id.imageView1);
			iv.setOnClickListener(onClickListener);
			//iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setTag(position);

			//localView.setLayoutParams(mImageViewLayoutParams);
			//imageView.setPadding(4, 4, 4, 4);
			holder = new ViewHolder();
			holder.setIv(iv);
			holder.setCv(cv);
			holder.setTv(tv);
			localView.setTag(holder);

			// and animate


		} else { // Otherwise re-use the converted view
			localView = convertView;
			holder = (ViewHolder) localView.getTag();
		}

		// set the values
		holder.getIv().setImageBitmap(BitmapFactory.decodeFile(item.getMediaThumbnail()));
		holder.getTv().setText(item.getTitle());
		holder.getCv().setText(item.getDescription());

		localView.setId(position);
		//localView.setAnimation(animation);list_element_centered.xml
		if (maxSeenIndex < position) {
			maxSeenIndex = position;
			Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide2);
			localView.startAnimation(animation);
		}

		return localView;
	}

	public Context getContext() {
		return context;
	}

	public void add(Theme testItem) {
		items.add(testItem);
		notifyDataSetChanged();
	}

}
