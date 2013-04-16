package com.ybi.fruityclock;

import greendroid.app.GDApplication;

import android.content.Intent;
import android.net.Uri;

import com.ybi.fruityclock.activities.FruityClockActivity;

public class FruityClockApplication extends GDApplication {

    @Override
    public Class<?> getHomeActivityClass() {
        return FruityClockActivity.class;
    }
    
    @Override
    public Intent getMainApplicationIntent() {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_url)));
    }

}
