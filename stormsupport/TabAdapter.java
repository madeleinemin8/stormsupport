package com.minlabs.stormsupport;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/* Tab technique from http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/ */

public class TabAdapter extends FragmentStatePagerAdapter {
    int tabCount;

    public TabAdapter (FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.tabCount = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabFragment_News newstab = new TabFragment_News();
                return newstab;
            case 1:
                TabFragmentMaps mapstab = new TabFragmentMaps();
                return mapstab;
            case 2:
                TabFragmentEvac evactab = new TabFragmentEvac();
                return evactab;
            case 3:
                TabFragmentLive livetab = new TabFragmentLive();
                return livetab;
            case 4:
                TabFragmentSocial socialtab = new TabFragmentSocial();
                return socialtab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

