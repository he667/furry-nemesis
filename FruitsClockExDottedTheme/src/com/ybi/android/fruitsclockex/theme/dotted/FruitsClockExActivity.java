package com.ybi.android.fruitsclockex.theme.dotted;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FruitsClockExActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fruitsclockex);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fruits_clock_ex, menu);
		return true;
	}

}
