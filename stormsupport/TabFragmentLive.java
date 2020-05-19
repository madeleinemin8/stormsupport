package com.minlabs.stormsupport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragmentLive extends Fragment {

    View originalView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        originalView = inflater.inflate(R.layout.activity_tab_fragment_live, container, false);
        return originalView;
    }

}
