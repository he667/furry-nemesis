package com.ybi.android.fruitsclockex;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ThemeAdapter extends BaseAdapter {

	private final List<Theme> items;
	private final Context context;

	private boolean mNotifyOnChange;
	private int maxSeenIndex;

	// Now handle the main ImageView thumbnails
	static ViewHolder holder;

	private final ImageLoadingListener animateFirstListener;
	private final DisplayImageOptions options;

	//private final OnClickListener onClickListener;

	public ThemeAdapter(Context context, List<Theme> listItems) {
		this.context = context;
		items = listItems;
		//		onClickListener = new OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//				Log.d(FruitsClockActivity.TAG, "Clicked " + v.getTag());
		//				Toast.makeText(ThemeAdapter.this.context, "azdazdazda", Toast.LENGTH_SHORT).show();
		//			}
		//		};

		options = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().displayer(new RoundedBitmapDisplayer(20)).build();

		animateFirstListener = new AnimateFirstDisplayListener();

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
			//Typeface someFont = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntul.ttf");

			//tv.setTypeface(someFont);
			//cv.setTypeface(someFont);

			// Add The Image!!!
			iv = (ImageView) localView.findViewById(R.id.imageView1);
			//iv.setOnClickListener(onClickListener);
			//iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setTag(position);

			//localView.setLayoutParams(mImageViewLayoutParams);
			//imageView.setPadding(4, 4, 4, 4);
			holder = new ViewHolder();
			holder.setIv(iv);
			holder.setCv(cv);
			holder.setTv(tv);
			localView.setTag(holder);

		} else { // Otherwise re-use the converted view
			localView = convertView;
			holder = (ViewHolder) localView.getTag();
		}

		// and animate
		if (item.getStatus() == Theme.STATUS_SELECTED) {
			holder.getTv().setTextColor(Color.argb(255, 0, 0, 255));
		} else {
			holder.getTv().setTextColor(Color.argb(255, 0, 0, 0));
		}

		// set the values
		//holder.getIv().setImageBitmap(BitmapFactory.decodeFile(item.getMediaThumbnail()));
		ImageLoader.getInstance().displayImage(item.getMediaThumbnail(), holder.getIv(), options, animateFirstListener);
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

	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
