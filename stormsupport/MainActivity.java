package com.minlabs.stormsupport;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView mWebView;
    WebView mWebView2;
    WebView mWebView3;
    WebView mWebView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Alerts"));
        tabLayout.addTab(tabLayout.newTab().setText("Flood Maps"));
        tabLayout.addTab(tabLayout.newTab().setText("Evac"));
        tabLayout.addTab(tabLayout.newTab().setText("Live"));
        tabLayout.addTab(tabLayout.newTab().setText("Social"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final TabAdapter tab_adapter = new TabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tab_adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 1){
                    loadMapsPage();
                }
                if(tab.getPosition() == 3){
                    loadLiveMapsPage();
                }
                if(tab.getPosition() == 4){
                    loadSocialMapsPage();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    public void loadMapsPage(){
        mWebView = (WebView) findViewById(R.id.webview);
        //final String url = "http://arcg.is/0uq8C5";
        final String url = "https://www.arcgis.com/apps/SimpleViewer/index.html?appid=a12c5614952e433baea38c290a02add0";
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);

    }
    public void loadLiveMapsPage(){
        mWebView2 = (WebView) findViewById(R.id.webview2);
        //final String url = "http://arcg.is/0uq8C5";
        final String url = "https://www.arcgis.com/apps/View/index.html?appid=9f1e50b56105422e87abb9dbd4d07493";
        mWebView2.setWebViewClient(new WebViewClient());
        mWebView2.getSettings().setJavaScriptEnabled(true);
        mWebView2.loadUrl(url);
    }
    public void loadSocialMapsPage(){
        mWebView3 = (WebView) findViewById(R.id.webview3);
        //final String url = "http://arcg.is/0uq8C5";
        final String url = "http://j.mp/2zj6sJa";
        mWebView3.setWebViewClient(new WebViewClient());
        mWebView3.getSettings().setJavaScriptEnabled(true);
        mWebView3.loadUrl(url);
    }
    public void loadFormPage(){
        mWebView4 = (WebView) findViewById(R.id.webview4);
        final String url = "";
        mWebView4.setWebViewClient(new WebViewClient());
        mWebView4.getSettings().setJavaScriptEnabled(true);
        mWebView4.loadUrl(url);
    }
}