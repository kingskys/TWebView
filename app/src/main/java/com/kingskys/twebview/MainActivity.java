package com.kingskys.twebview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.twebview.twebview.TWebView;

public class MainActivity extends AppCompatActivity {

    private TWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        webView = findViewById(R.id.view_webview);
        webView.setPullEnabled(false);

        findViewById(R.id.btn_open1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView != null) {
                    String url = "http://www.baidu.com";
                    webView.loadUrl(url);
                }
            }
        });

        findViewById(R.id.btn_open2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView != null) {
                    String url = "alipays://platformapi/startapp?appId=20000123";
                    webView.openApp(url);
                }
            }
        });

        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView != null) {
                    webView.reloadWebView();
                }
            }
        });

    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                onBackPressed();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
