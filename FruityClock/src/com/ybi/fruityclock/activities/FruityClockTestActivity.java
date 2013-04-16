package com.ybi.fruityclock.activities;

import greendroid.app.GDActivity;
import greendroid.widget.PageIndicator;
import greendroid.widget.PagedAdapter;
import greendroid.widget.PagedView;
import greendroid.widget.PagedView.OnPagedViewChangeListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ybi.fruityclock.R;

public class FruityClockTestActivity extends GDActivity {

    private static final int PAGE_COUNT = 7;
    private static final int PAGE_MAX_INDEX = PAGE_COUNT - 1;

    private PageIndicator mPageIndicatorOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTitle(R.string.app_name);

        setActionBarContentView(R.layout.paged_view);

        final PagedView pagedView = (PagedView) findViewById(R.id.paged_view);
        pagedView.setOnPageChangeListener(mOnPagedViewChangedListener);
        pagedView.setAdapter(new PhotoSwipeAdapter());

        mPageIndicatorOther = (PageIndicator) findViewById(R.id.page_indicator_other);
        mPageIndicatorOther.setDotCount(PAGE_COUNT);
        mPageIndicatorOther.setDotSpacing(-4);
        
        
        
        setActivePage(pagedView.getCurrentPage());
    }
    
    private void setActivePage(int page) {
        mPageIndicatorOther.setActiveDot(page);
    }
    
    private OnPagedViewChangeListener mOnPagedViewChangedListener = new OnPagedViewChangeListener() {

        @Override
        public void onStopTracking(PagedView pagedView) {
        }

        @Override
        public void onStartTracking(PagedView pagedView) {
        }

        @Override
        public void onPageChanged(PagedView pagedView, int previousPage, int newPage) {
            setActivePage(newPage);
        }
    };
    
    private class PhotoSwipeAdapter extends PagedAdapter {
        
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list, parent, false);
            }

            //((TextView) convertView).setText(Integer.toString(position));

            return convertView;
        }

    }
}
