package com.minlabs.stormsupport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    private WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebview = new WebView(this);
        mWebview.loadUrl(getIntent().getStringExtra("address"));
        setContentView(mWebview);
    }
}
