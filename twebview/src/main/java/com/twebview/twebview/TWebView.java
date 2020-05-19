package com.twebview.twebview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.base.loadingview.LoadingView;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class TWebView extends LinearLayout {
    private PtrClassicFrameLayout pFrame = null;
    private WebView webView = null;
    private LoadingView loadingView = null;
    private WeakReference<Context> context = null;
    private WeakReference<Activity> activityWeakReference = null;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public TWebView(Context context) {
        this(context, null);
    }

    public TWebView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TWebView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = new WeakReference<>(context);
        if (context instanceof Activity) {
            activityWeakReference = new WeakReference<>((Activity) context);
        }
        initView();
    }

    public void setPullEnabled(boolean b) {
        if (pFrame != null) {
            pFrame.setEnabled(b);
        }
    }

    private void log(String msg) {
        Log.d("TWebView", "TWebView - msg - " + msg);
    }

    private void initView() {
//        log("initView context = "+context.get());
        View view = LayoutInflater.from(context.get()).inflate(R.layout.tview_webview, this, true);

        loadingView = view.findViewById(R.id.tview_loadingview);
        loadingView.hide();

        createWebView(view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void createWebView(View view) {
//        log("createWebView");
        webView = (WebView) view.findViewById(R.id.tview_webview);
        initFrameView(view);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.supportMultipleWindows();
        settings.setAllowContentAccess(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSaveFormData(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setBlockNetworkImage(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void back() {
                if (activityWeakReference != null && activityWeakReference.get() != null) {
                    activityWeakReference.get().finish();
                }
            }

            @JavascriptInterface
            public void openApp(String url) {
                TWebView.this.openApp(url);
            }
        }, "atools");

        webView.setWebViewClient(new TWebViewClient(getContext()) {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoadingView();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (pFrame != null) {
                    pFrame.refreshComplete();
                }
                hideLoadingView();
            }
        });

    }

    private void showLoadingView() {
//        log("showLoadingView");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null) {
                    loadingView.show();
                }
            }
        });
    }

    private void hideLoadingView() {
//        log("hideLoadingView");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingView != null) {
                    loadingView.hide();
                }
            }
        });

    }

    private void initFrameView(View view) {
        pFrame = (PtrClassicFrameLayout) view.findViewById(R.id.tview_webframe);

        pFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (webView != null) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, webView, header);
                }
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                reloadWebView();
            }
        });

        pFrame.setResistance(1.7f);
        pFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        pFrame.setDurationToClose(200);
        pFrame.setDurationToCloseHeader(1000);
//        // default is false
        pFrame.setPullToRefresh(false);
//        // default is true
        pFrame.setKeepHeaderWhenRefresh(true);
    }

    public void setFullScreen(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        ViewGroup.LayoutParams params = pFrame.getLayoutParams();
        int height = rect.height() - getStatusBarHeight(activity.getApplicationContext());
        params.height = height;
    }

    public void reloadWebView() {
        if (webView != null) {
            webView.reload();
        }
    }

    public void loadUrl(String url) {
        if (webView != null) {
            if (!url.contains("://")) {
                url = "http://" + url;
            }
            webView.loadUrl(url);
        }
    }

    public boolean canGoBack() {
        if (webView != null) {
            return webView.canGoBack();
        }
        return false;
    }

    public void goBack() {
        if (webView != null) {
            webView.goBack();
        }
    }

    public void openApp(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        } catch (Throwable e) {}
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private final void runOnUiThread(Runnable action) {
        mHandler.post(action);
    }
}
