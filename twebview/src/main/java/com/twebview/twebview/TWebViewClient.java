package com.twebview.twebview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TWebViewClient  extends WebViewClient {

    public TWebViewClient() {
        super();
    }

    private void log(String msg) {
        Log.d("TWebView", "TWebView - msg - " + msg);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        log("shouldOverrideUrlLoading - url = " + url);

        if (TextUtils.isEmpty(url)) {
            return false;
        }

        view.loadUrl(url);

        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return shouldOverrideUrlLoading(view, request.getUrl().toString());
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//        log("onPageStarted - url = " + url);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
//        log("onPageFinished - url = " + url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
//        log("onLoadResource - url = " + url);
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
//        log("onPageCommitVisible - url = " + url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//        log("shouldInterceptRequest - url = " + url);
        return null;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view,
                                                      WebResourceRequest request) {
//        String url = request.getUrl().toString();
//        log("shouldInterceptRequest - url = " + url);
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
//        log("onReceivedError url = " + failingUrl);
//        log("errorCode = " + errorCode);
//        log("description = " + description);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm,
                                       String account, String args) {
//        log("onReceivedLoginRequest - account = " + account + ", args = " + args);
//        log("realm = " + realm);
    }

    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
//        log("onRenderProcessGone detail = " + detail);
        return false;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                   SslError error) {
//        log("onReceivedSslError - " + error);
        handler.cancel();
    }

}
