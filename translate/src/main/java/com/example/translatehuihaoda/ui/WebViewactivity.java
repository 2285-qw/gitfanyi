package com.example.translatehuihaoda.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.translatehuihaoda.R;

public class WebViewactivity extends AppCompatActivity {

    private BaseWebView webView;
    private ProgressBar progressBar;

    public static void openActivity(Context context, String url) {
        Log.d("URL",url);

        Intent intent = new Intent(context, WebViewactivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        setContentView(R.layout.activity_web_viewactivity);

        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.pb_web_base);

        webView.setProgressBar(progressBar);
        webView.loadUrl(url);

    }
}