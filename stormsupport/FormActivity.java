package com.minlabs.stormsupport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FormActivity extends AppCompatActivity {

    WebView mWebView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Intent intent = getIntent();
        final String url = intent.getStringExtra(TabFragmentSocial.FORM_URL);
        mWebView4 = (WebView) findViewById(R.id.webview4);
        mWebView4.setWebViewClient(new WebViewClient());
        mWebView4.getSettings().setJavaScriptEnabled(true);
        mWebView4.loadUrl(url);

    }
}
