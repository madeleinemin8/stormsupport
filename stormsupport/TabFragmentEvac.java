package com.minlabs.stormsupport;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFragmentEvac extends Fragment {

    View originalview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        originalview =  inflater.inflate(R.layout.activity_tab_fragment_evac, container, false);
        TextView link1 = (TextView) originalview.findViewById(R.id.link1);
        Linkify.addLinks(link1, Linkify.ALL);
        TextView link2 = (TextView) originalview.findViewById(R.id.link2);
        Linkify.addLinks(link2, Linkify.ALL);
        TextView link3 = (TextView) originalview.findViewById(R.id.link3);
        Linkify.addLinks(link3, Linkify.ALL);
        return originalview;
    }
}
