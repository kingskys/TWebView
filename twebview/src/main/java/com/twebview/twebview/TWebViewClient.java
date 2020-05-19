package com.twebview.twebview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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

import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.List;

public class TWebViewClient  extends WebViewClient {
    private WeakReference<Context> weakReference = null;

    public TWebViewClient(Context context) {
        super();
        if (context != null) {
            weakReference = new WeakReference<Context>(context);
        }
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

        if (url.startsWith("http://") || url.startsWith("https://")) {
//            view.loadUrl(url); // 如果使用loadUrl，有的界面会不能返回
//            return true;
            return false;
        }

        if (queryActiviesCount(url) > 0 && openActivity(url)) {
            return true;
        }

        return true;
    }

    private int queryActiviesCount(String url) {
        try {
            if (weakReference == null || weakReference.get() == null) {
                return 0;
            }
            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            PackageManager packageManager = weakReference.get().getPackageManager();
            List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            return list.size();
        } catch (Throwable e) {
            return 0;
        }
    }

    private boolean openActivity(String url) {
        try {
            if (weakReference == null || weakReference.get() == null) {
                return false;
            }

            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            PackageManager packageManager = weakReference.get().getPackageManager();
            ResolveInfo resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfo != null) {
                weakReference.get().startActivity(intent);
                return true;
            }
        } catch (Throwable e) {}
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
        super.onReceivedLoginRequest(view, realm, account, args);
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
