package biz.remu.app.AndroidWebViewDemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

public class WebViewActivity extends SherlockActivity {

    private WebView mWebView;
    private SherlockActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);

        mWebView = (WebView)findViewById(R.id.webView);
        mActivity = this;

        // Google検索のロード
        mWebView.loadUrl("https://www.google.com/");
        setTitle("Now Loading...");

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        // Zoomの有効化
        mWebView.getSettings().setBuiltInZoomControls(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mActivity.setSupportProgressBarIndeterminateVisibility(true);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                mActivity.setTitle(mWebView.getTitle());
                mActivity.setSupportProgressBarIndeterminateVisibility(false);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });

        // JSの有効化
        mWebView.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add("戻る")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){
                        if(mWebView.canGoBack()){
                            mWebView.goBack();
                        }
                        return true;
                    }
                })
                .setIcon(android.R.drawable.ic_media_rew)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add("進む")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){
                        if(mWebView.canGoForward()){
                            mWebView.goForward();
                        }
                        return true;
                    }
                })
                .setIcon(android.R.drawable.ic_media_ff)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add("更新")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        mWebView.reload();
                        return true;
                    }
                })
                .setIcon(android.R.drawable.ic_menu_rotate)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add("共有")
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        String shareText = mWebView.getTitle() + " - " + mWebView.getUrl();
                        i.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
                        startActivity(i);
                        return true;
                    }
                })
                .setIcon(R.drawable.abs__ic_menu_share_holo_dark)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }
}
