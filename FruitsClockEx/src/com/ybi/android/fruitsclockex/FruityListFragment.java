package com.ybi.android.fruitsclockex;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class FruityListFragment extends ListFragment {

	private MainAdapter adapter;
	private final ArrayList<Theme> items;
	private FruityListClickListener listener;
	public FruityListFragment() {
		items = new ArrayList<Theme>();
	}

	@Override
	public void onStart() {
		super.onStart();
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (listener != null) {
					listener.onClick(arg2);
				}
			}
		});
	}

	public MainAdapter getAdapter(Context context) {
		if (adapter == null) {
			adapter = new MainAdapter(context, items);
			setListAdapter(adapter);
		}
		return adapter;
	}

	public ArrayList<Theme> getItems() {
		return items;
	}

	public void attach(FruityListClickListener listener) {
		this.listener = listener;
	}

}
