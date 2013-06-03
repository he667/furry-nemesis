package com.ybi.android.fruitsclockex;

import java.util.Iterator;
import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.dataframework.DataFramework;
import com.android.dataframework.Entity;

public class FruitsClockActivity extends FragmentActivity {

	public static final String TAG = "FruitsClockEx";
	private static final int AVAILABLE_FRAGMENT_TAG = 0;
	private static final int INSTALLED_FRAGMENT_TAG = 1;
	private static final int REQUEST_CODE = 1;

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private RefreshTask task;
	private Menu optionsMenu;

	private int currentAvailableThemeId;
	private int currentInstalledThemeId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// database connectivity
		openDatabase();

		// initialize the list if it's empty
		initiate();

	}

	private void initiate() {
		int count = DataFramework.getInstance().getCursor("themes").getCount();
		if (count == 0) {
			startRefreshTask();
		}
	}

	private void startRefreshTask() {
		task = (RefreshTask) getLastCustomNonConfigurationInstance();

		if (task == null) {
			task = new RefreshTask(this);
			task.execute();
		} else {
			task.attach(this);
			onProgressUpdate(task.getProgress());

			if (task.getProgress() >= 100) {
				onPostExecute();
			}
		}
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		task.detach();

		return task;
	}

	public void onPreExecute() {
		Toast.makeText(getApplicationContext(), "Downloading themes.", Toast.LENGTH_SHORT).show();
		setRefreshActionButtonState(true);
	}

	void onProgressUpdate(int progress) {
		Toast.makeText(getApplicationContext(), "Checking for new themes...", Toast.LENGTH_SHORT).show();
	}

	void onPostExecute() {
		Toast.makeText(getApplicationContext(), "Themes update complete.", Toast.LENGTH_SHORT).show();
		setRefreshActionButtonState(false);

		// refresh the available themes
		FruitsListFragment availableFragment =
				(FruitsListFragment) getSupportFragmentManager().findFragmentByTag(
						makeFragmentName(R.id.pager, AVAILABLE_FRAGMENT_TAG));
		populateList(new int[] { Theme.STATUS_AVAILABLE }, availableFragment);
		availableFragment.getAdapter(getApplicationContext()).notifyDataSetChanged();

		// refresh the installed / selected themes
		FruitsListFragment installedFragment =
				(FruitsListFragment) getSupportFragmentManager().findFragmentByTag(
						makeFragmentName(R.id.pager, INSTALLED_FRAGMENT_TAG));
		populateList(new int[] { Theme.STATUS_INSTALLED, Theme.STATUS_SELECTED }, installedFragment);
		installedFragment.getAdapter(getApplicationContext()).notifyDataSetChanged();
	}

	private void openDatabase() {

		//deleteDatabase("themes_db");

		try {
			DataFramework.getInstance().open(this, "com.ybi.android.fruitsclockex");
		} catch (Exception e) {
			Log.e(TAG, "Error getting dataframework instance", e);
		}



		//
		//		Theme thme = new Theme();
		//		//theme.setTid(i);
		//		thme.setDate(System.currentTimeMillis());
		//		thme.setDescription("A classical flipping number clock design.");
		//		thme.setLink("com.ybi.android.fruitsclockex.theme.lunar");
		//		thme.setLocation("http://www.playstore.com");
		//		thme.setMediaContent("http://www.something.com/themename/theme.zip");
		//		thme.setMediaThumbnail("https://lh6.ggpht.com/sRNlm2cvVKdTRZrK7_VHhmzNPC03wSVX0bXyLlcX05l9dUxV67z5-eXYR_DxxDtLeQ=w256");
		//		thme.setStatus(Theme.STATUS_AVAILABLE);
		//		thme.setTitle("Lunar Theme");
		//		thme.toEntity().save();

		//		for (int i = 1; i < 10; i++) {
		//			Theme theme = new Theme();
		//			//theme.setTid(i);
		//			theme.setDate(System.currentTimeMillis());
		//			theme.setDescription("blablabla");
		//			theme.setLink("com.ybi.android.fruitsclockex.theme.something");
		//			theme.setLocation("http://www.playstore.com");
		//			theme.setMediaContent("http://www.something.com/themename/theme.zip");
		//			theme.setMediaThumbnail("https://lh3.ggpht.com/M095gJm09oc3p4Ixb5sEEFDzjQW_shGqq78dSGfXj0AZdB5zmdHAmRhRsq08lcKhjw=w256");
		//			theme.setStatus(Theme.STATUS_AVAILABLE);
		//			theme.setTitle("Theme #" + i);
		//			theme.toEntity().save();
		//		}

		//		List<Entity> categories = DataFramework.getInstance().getEntityList("themes");
		//		Iterator<Entity> iter = categories.iterator();
		//		while (iter.hasNext()) {
		//			Entity ent = iter.next();
		//			Theme theme = Theme.fromEntity(ent);
		//			Log.d(TAG, "Theme #" + theme.getTid() + " -- Title=" + theme.getTitle());
		//			Log.d(TAG, "Theme #" + theme.getTid() + " -- Status=" + theme.getStatus());
		//			theme.setLink("com.ovh.android.cloudnasfrontend");
		//			theme.toEntity().save();
		//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		optionsMenu = menu;
		return super.onCreateOptionsMenu(menu);
	}

	public void setRefreshActionButtonState(final boolean refreshing) {
		if (optionsMenu != null) {
			final MenuItem refreshItem = optionsMenu.findItem(R.id.refresh);
			if (refreshItem != null) {
				if (refreshing) {
					refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
				} else {
					refreshItem.setActionView(null);
				}
			}
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			if (position == 0) {
				Fragment availableFragment = prepareListFragement(new int[] { Theme.STATUS_AVAILABLE });
				((FruitsListFragment) availableFragment).attach(availableClickListener);
				//getSupportFragmentManager().beginTransaction().add(availableFragment, AVAILABLE_FRAGMENT_TAG).commit();
				return availableFragment;
			} else if (position == 1) {
				Fragment installedFragment = prepareListFragement(new int[] { Theme.STATUS_INSTALLED, Theme.STATUS_SELECTED });
				((FruitsListFragment) installedFragment).attach(installedClickListener);
				//getSupportFragmentManager().beginTransaction().add(installedFragment, AVAILABLE_FRAGMENT_TAG).commit();
				return installedFragment;
			} else {
				Fragment fragment = new GenericSectionFragment();
				Bundle args = new Bundle();
				args.putInt(GenericSectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1);
			case 1:
				return getString(R.string.title_section2);
			case 2:
				return getString(R.string.title_section3);
			}
			return null;
		}
	}

	public static class GenericSectionFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		public GenericSectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.about_layout, null);
			return view;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DataFramework.getInstance().close();
	}

	private Fragment prepareListFragement(int[] status) {
		FruitsListFragment fragment = new FruitsListFragment();

		// populate with the correct status
		populateList(status, fragment);

		// nothing more
		fragment.getAdapter(getApplicationContext()).stopAppending();
		return fragment;
	}

	private void populateList(int[] status, FruitsListFragment fragment) {
		// construct the status thing
		StringBuilder statusList = new StringBuilder();
		for (int i = 0; i < status.length; i++) {
			statusList.append("status=" + status[i]);
			if (i < status.length - 1) {
				statusList.append(" OR ");
			}
		}
		List<Entity> categories = DataFramework.getInstance().getEntityList("themes", statusList.toString());
		fragment.getItems().clear();
		Iterator<Entity> iter = categories.iterator();
		while (iter.hasNext()) {
			Entity ent = iter.next();
			fragment.getItems().add(Theme.fromEntity(ent));
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.refresh:
			startRefreshTask();
			return true;
		default:
			return super.onMenuItemSelected(featureId, item);
		}

	}

	static class RefreshTask extends AsyncTask<Void, Void, Void> {
		FruitsClockActivity activity = null;
		int progress = 0;

		RefreshTask(FruitsClockActivity activity) {
			attach(activity);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			activity.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... unused) {
			publishProgress();

			ThemeManager.downloadThemes(activity.getApplicationContext());
			// let's do some things here
			//			List<Entity> categories = DataFramework.getInstance().getEntityList("themes", "status=1");
			//
			//			ArrayList<Theme> items = new ArrayList<Theme>();
			//
			//			Iterator<Entity> iter = categories.iterator();
			//			while (iter.hasNext()) {
			//				Entity ent = iter.next();
			//				items.add(Theme.fromEntity(ent));
			//			}
			//
			//			ObjectMapper mapper = new ObjectMapper();
			//			String result;
			//			try {
			//				result = mapper.writeValueAsString(items);
			//				Log.d(FruityClockActivity.TAG, "Result === " + result);
			//			} catch (JsonProcessingException e) {
			//				Log.d(FruityClockActivity.TAG, "Error === ", e);
			//			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... unused) {
			if (activity == null) {
				Log.w("RotationAsync", "onProgressUpdate() skipped -- no activity");
			} else {
				progress += 5;
				activity.onProgressUpdate(progress);
			}
		}

		@Override
		protected void onPostExecute(Void unused) {
			if (activity == null) {
				Log.w("RotationAsync", "onPostExecute() skipped -- no activity");
			} else {
				activity.onPostExecute();
			}
		}

		void detach() {
			activity = null;
		}

		void attach(FruitsClockActivity activity) {
			this.activity = activity;
		}

		int getProgress() {
			return progress;
		}
	}

	private static String makeFragmentName(int viewId, int index) {
		return "android:switcher:" + viewId + ":" + index;
	}

	private final FruitsListClickListener availableClickListener = new FruitsListClickListener() {
		@Override
		public void onClick(int position) {
			// do not forget
			currentAvailableThemeId = position;

			// get the theme
			FruitsListFragment fragment =
					(FruitsListFragment) getSupportFragmentManager().findFragmentByTag(
							makeFragmentName(R.id.pager, AVAILABLE_FRAGMENT_TAG));

			Theme theme = fragment.getItems().get(position);

			// open the play store where the theme is located
			try {
				startActivityForResult(
						new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + theme.getLink())),
						REQUEST_CODE);
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivityForResult(
						new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + theme.getLink())),
						REQUEST_CODE);
			}

		}
	};
	private final FruitsListClickListener installedClickListener = new FruitsListClickListener() {

		@Override
		public void onClick(int position) {

			// do not forget
			currentInstalledThemeId = position;

			// get the fragment
			FruitsListFragment installedFragment =
					(FruitsListFragment) getSupportFragmentManager().findFragmentByTag(
							makeFragmentName(R.id.pager, INSTALLED_FRAGMENT_TAG));

			// retrieve the theme
			Theme theme = installedFragment.getItems().get(currentInstalledThemeId);

			// select a theme as active
			if (ThemeManager.themeInstalledOrNot(theme, getApplicationContext())) {
				// toast the selection
				Toast.makeText(getApplicationContext(), "Installing " + theme.getTitle(), Toast.LENGTH_SHORT).show();

				// unselect the only supposed one theme
				List<Entity> categories = DataFramework.getInstance().getEntityList("themes", "status=" + Theme.STATUS_SELECTED);
				Iterator<Entity> iter = categories.iterator();
				while (iter.hasNext()) {
					Entity ent = iter.next();
					Theme localTheme = Theme.fromEntity(ent);
					localTheme.setStatus(Theme.STATUS_INSTALLED);
					localTheme.toEntity().save();
				}

				// change status
				theme.setStatus(Theme.STATUS_SELECTED);
				theme.toEntity().save();

				// refresh the fragment
				populateList(new int[] { Theme.STATUS_INSTALLED, Theme.STATUS_SELECTED }, installedFragment);
				installedFragment.getAdapter(getApplicationContext()).notifyDataSetChanged();

				// save the theme to the sharedpreferences so we don't rely on the database anymore
				final SharedPreferences prefs = getApplicationContext().getSharedPreferences(NumberFactory.PREFS_FILE, 0);
				Editor editor = prefs.edit();
				editor.putString(NumberFactory.PREFS_LINK, theme.getLink());
				editor.putString(NumberFactory.PREFS_TITLE, theme.getTitle());
				editor.commit();

				// refresh the widgets
				refreshWidget();
			}

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// retrieve the selected app
		if (requestCode == REQUEST_CODE && currentAvailableThemeId >= 0) {
			// retrieve the fragment
			FruitsListFragment availableFragment =
					(FruitsListFragment) getSupportFragmentManager().findFragmentByTag(
							makeFragmentName(R.id.pager, AVAILABLE_FRAGMENT_TAG));

			FruitsListFragment installedFragment =
					(FruitsListFragment) getSupportFragmentManager().findFragmentByTag(
							makeFragmentName(R.id.pager, INSTALLED_FRAGMENT_TAG));

			// retrieve the theme
			Theme theme = availableFragment.getItems().get(currentAvailableThemeId);

			// check if the theme is available
			if (ThemeManager.themeInstalledOrNot(theme, getApplicationContext())) {
				// mark it as installed
				theme.setStatus(Theme.STATUS_INSTALLED);
				theme.toEntity().save();

				// now is the good time to refresh the two fragment
				// 1. Available
				populateList(new int[] { Theme.STATUS_AVAILABLE }, availableFragment);
				availableFragment.getAdapter(getApplicationContext()).notifyDataSetChanged();

				// 2. Installed
				populateList(new int[] { Theme.STATUS_INSTALLED, Theme.STATUS_SELECTED }, installedFragment);
				installedFragment.getAdapter(getApplicationContext()).notifyDataSetChanged();
			}
		}
	}

	protected void refreshWidget() {
		Intent intent = new Intent(this, FruitsClockWidgetProvider.class);
		intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
		// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
		// since it seems the onUpdate() is only fired on that:
		int[] ids = { R.xml.widget_fruitsclockex };
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		sendBroadcast(intent);
	}

}
