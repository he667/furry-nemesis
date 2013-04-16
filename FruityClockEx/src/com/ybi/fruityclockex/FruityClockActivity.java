package com.ybi.fruityclockex;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FruityClockActivity extends Activity {

	public static final String TAG = "FruityClockEx";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fruity_clock);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_fruity_clock, menu);
		return true;
	}

}
