package com.seven.mall.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.seven.lib_common.base.fragment.BaseFragment;
import com.seven.lib_common.stextview.TypeFaceView;
import com.seven.mall.R;

import butterknife.BindView;

/**
 * @auhtor seven
 * email:frankchenlei@163.com
 * 2019/5/12
 */
@SuppressLint("ValidFragment")
public class WebFragment extends BaseFragment {

    private String url;

    @BindView(R.id.web_view)
    public WebView webView;
    @BindView(R.id.title_tv)
    public TypeFaceView titleTv;
    @BindView(R.id.left_btn)
    public RelativeLayout leftBtn;

    public WebFragment() {
    }

    public WebFragment(String url) {
        this.url = url;
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_web;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        if (TextUtils.isEmpty(url)) return;

        leftBtn.setOnClickListener(this);
        titleTv.setText(R.string.loading);

        initWebView(url);

        config();
    }

    private void initWebView(String url) {

        webView.getSettings().setJavaScriptEnabled(true);
//        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");

        webView.loadUrl(url);
    }

    private void config() {
        // 设置具体WebViewClient
        webView.setWebViewClient(new ActivityWebViewClient());

        //设置WebChromeClient类
        webView.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (titleTv != null)
                    titleTv.setText(title);
            }

            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            }
        });
    }

    private class ActivityWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("seven:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//            view.loadUrl("javascript:window.java_obj.shareTitle("
//                    + "document.querySelector('meta[name=\"share_title\"]').getAttribute('content')"
//                    + ");");
//            view.loadUrl("javascript:window.java_obj.shareDescription("
//                    + "document.querySelector('meta[name=\"share_description\"]').getAttribute('content')"
//                    + ");");
//            view.loadUrl("javascript:window.java_obj.shareUrl("
//                    + "document.querySelector('meta[name=\"share_url\"]').getAttribute('content')"
//                    + ");");
//            view.loadUrl("javascript:window.java_obj.shareImageUrl("
//                    + "document.querySelector('meta[name=\"share_image_url\"]').getAttribute('content')"
//                    + ");");
            super.onPageFinished(view, url);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.left_btn:
                if (webView.canGoBack())
                    webView.goBack();
                else
                    getActivity().onBackPressed();
                break;
        }


    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    public WebView getView() {
        return webView;
    }

}
