package com.example.study;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class webView extends AppCompatActivity {
   WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webview= (WebView) findViewById(R.id.webview);
        webview.loadUrl(getIntent().getStringExtra("LINK"));
    }
}