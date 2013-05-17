package com.ybi.fruityclockex;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class MainAdapter extends EndlessAdapter {
	private AlphaAnimation rotate = null;
	private View pendingView = null;
	private final Context context;

	public MainAdapter(Context ctxt, ArrayList<Theme> list) {
		super(new ThemeAdapter(ctxt, list));
		context = ctxt;
		rotate = new AlphaAnimation(1.0f, 0.0f);
		rotate.setDuration(600);
		rotate.setRepeatMode(Animation.RESTART);
		rotate.setRepeatCount(Animation.INFINITE);


		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		Log.d(FruityClockActivity.TAG, "WINDOWS SIZE W = " + width);
		Log.d(FruityClockActivity.TAG, "WINDOWS SIZE H = " + height);
		//02-25 18:52:53.994: D/INR(28520): WINDOWS SIZE W = 768
		//02-25 18:52:53.994: D/INR(28520): WINDOWS SIZE H = 1184

	}

	@Override
	protected View getPendingView(ViewGroup parent) {
		View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_centered, null);

		pendingView = row.findViewById(R.id.textView1);
		pendingView.setVisibility(View.VISIBLE);

		//		pendingView = row.findViewById(R.id.throbber);
		//		pendingView.setVisibility(View.VISIBLE);
		startProgressAnimation();

		return row;
	}

	@Override
	protected boolean cacheInBackground() {
		//SystemClock.sleep(20); // pretend to do work

		return getWrappedAdapter().getCount() < 75;
	}

	@Override
	protected void appendCachedData() {
		if (getWrappedAdapter().getCount() < 75) {
			//			FeedItemAdapter a = (FeedItemAdapter) getWrappedAdapter();
			//			for (int i = 0; i < 2; i++) {
			//
			//				FeedItem testItem = new FeedItem();
			//				testItem.setTitle("Lorem Ipsum" + a.getCount());
			//				testItem.setDate(Calendar.getInstance().getTime());
			//				testItem.setDescription(testItem.getDateForDisplay() + " / Dolor sit amet consectur");
			//				testItem.setLink("http://www.google.fr");
			//				testItem.setMediaThumbnail("/mnt/sdcard/Pictures/Boston2.jpg");
			//				a.add(testItem);
			//			}
		}
	}

	void startProgressAnimation() {
		if (pendingView != null) {
			pendingView.startAnimation(rotate);
		}
	}

}