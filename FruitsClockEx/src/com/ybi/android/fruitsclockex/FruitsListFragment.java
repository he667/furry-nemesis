package com.ybi.android.fruitsclockex;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class FruitsListFragment extends ListFragment {

	private MainAdapter adapter;
	private final ArrayList<Theme> items;
	private FruitsListClickListener listener;
	public FruitsListFragment() {
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_layout, null);
		return view;
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

	public void attach(FruitsListClickListener listener) {
		this.listener = listener;
	}

}
