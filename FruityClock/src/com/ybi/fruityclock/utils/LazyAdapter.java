package com.ybi.fruityclock.utils;

import java.util.ArrayList;

import com.ybi.fruityclock.R;
import com.ybi.fruityclock.R.id;
import com.ybi.fruityclock.R.layout;
import com.ybi.fruityclock.bo.Theme;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<Theme> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    
    public LazyAdapter(Activity a, ArrayList<Theme> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
    	if (data != null)
        	return data.size();
    	else
    		return 0;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.themelist, null);

        TextView text=(TextView)vi.findViewById(R.id.title);;
        TextView detail=(TextView)vi.findViewById(R.id.detail);;
        ImageView image=(ImageView)vi.findViewById(R.id.img);
        text.setText(data.get(position).getName());
        detail.setText(data.get(position).getDetail());
        imageLoader.DisplayImage(data.get(position), activity, image);
        return vi;
    }
}