package com.minlabs.stormsupport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TabFragmentSocial extends Fragment {

    public static final String FORM_URL = "com.minlabs.stormsupport.MESSAGE";
    View originalView;
    Button mButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        originalView = inflater.inflate(R.layout.activity_tab_fragment_social, container, false);
        mButton = (Button) originalView.findViewById(R.id.form_button);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FormActivity.class);
                String url = "https://goo.gl/forms/9BixUTkIBIEkESKf2";
                intent.putExtra(FORM_URL, url);
                startActivity(intent);
            }
        });

        return originalView;
    }
}
