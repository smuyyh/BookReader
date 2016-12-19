package com.justwayward.reader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.justwayward.reader.R;
import com.justwayward.reader.base.BaseActivity;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.component.AppComponent;
import com.justwayward.reader.view.chmview.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * @author yuyh.
 * @date 2016/12/19.
 */
public class ReadCHMActivity extends BaseActivity {

    public static void start(Context context, String filePath) {
        Intent intent = new Intent(context, ReadCHMActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.fromFile(new File(filePath)));
        context.startActivity(intent);
    }

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.webview)
    WebView mWebView;

    private String chmFileName;
    public String chmFilePath = "", extractPath, md5File;

    private ArrayList<String> listSite;
    private ArrayList<String> listBookmark;

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_chm;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        chmFilePath = Uri.decode(getIntent().getDataString().replace("file://", ""));
        chmFileName = chmFilePath.substring(chmFilePath.lastIndexOf("/") + 1, chmFilePath.lastIndexOf("."));
        mCommonToolbar.setTitle(chmFileName);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        Utils.chm = null;
        listSite = new ArrayList<>();
    }

    @Override
    public void configViews() {
        initVweView();

        initFile();
    }

    private void initVweView() {
        mProgressBar.setMax(100);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                visible(mProgressBar);
                mProgressBar.setProgress(newProgress);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!url.startsWith("http") && !url.endsWith(md5File)) {
                    String temp = url.substring("file://".length());
                    if (!temp.startsWith(extractPath)) {
                        url = "file://" + extractPath + temp;
                    }
                }

                super.onPageStarted(view, url, favicon);
                mProgressBar.setProgress(50);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setProgress(100);
                gone(mProgressBar);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                if (!url.startsWith("http") && !url.endsWith(md5File)) {
                    String temp = url.substring("file://".length());
                    if (!temp.startsWith(extractPath)) {
                        url = "file://" + extractPath + temp;
                    }
                }
                super.onLoadResource(view, url);
            }


            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (!url.startsWith("http") && !url.endsWith(md5File)) {
                    String temp = url.substring("file://".length());
                    String insideFileName;
                    if (!temp.startsWith(extractPath)) {
                        url = "file://" + extractPath + temp;
                        insideFileName = temp;
                    } else {
                        insideFileName = temp.substring(extractPath.length());
                    }
                    if (insideFileName.contains("#")) {
                        insideFileName = insideFileName.substring(0, insideFileName.indexOf("#"));
                    }
                    if (insideFileName.contains("?")) {
                        insideFileName = insideFileName.substring(0, insideFileName.indexOf("?"));
                    }
                    if (insideFileName.contains("%20")) {
                        insideFileName = insideFileName.replaceAll("%20", " ");
                    }
                    if (url.endsWith(".gif") || url.endsWith(".jpg") || url.endsWith(".png")) {
                        try {
                            return new WebResourceResponse("image/*", "", Utils.chm.getResourceAsStream(insideFileName));
                        } catch (IOException e) {
                            e.printStackTrace();
                            return super.shouldInterceptRequest(view, request);
                        }
                    } else if (url.endsWith(".css") || url.endsWith(".js")) {
                        try {
                            return new WebResourceResponse("", "", Utils.chm.getResourceAsStream(insideFileName));
                        } catch (IOException e) {
                            e.printStackTrace();
                            return super.shouldInterceptRequest(view, request);
                        }
                    } else {
                        Utils.extractSpecificFile(chmFilePath, extractPath + insideFileName, insideFileName);
                    }
                }
                Log.e("2, webviewrequest", url);
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http") && !url.endsWith(md5File)) {
                    String temp = url.substring("file://".length());
                    if (!temp.startsWith(extractPath)) {
                        url = "file://" + extractPath + temp;
                        view.loadUrl(url);
                        return true;
                    }
                }
                return false;
            }

            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrlLoading(view, request.getUrl().toString());
            }
        });
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
    }

    private void initFile() {
        new AsyncTask<Void, Void, Void>() {
            int historyIndex = 1;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                md5File = Utils.checkSum(chmFilePath);
                extractPath = Constant.PATH_CHM + "/" + md5File;
                if (!(new File(extractPath).exists())) {
                    if (Utils.extract(chmFilePath, extractPath)) {
                        try {
                            listSite = Utils.domparse(chmFilePath, extractPath, md5File);
                            listBookmark = Utils.getBookmark(extractPath, md5File);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        (new File(extractPath)).delete();
                    }
                } else {
                    listSite = Utils.getListSite(extractPath, md5File);
                    listBookmark = Utils.getBookmark(extractPath, md5File);
                    historyIndex = Utils.getHistory(extractPath, md5File);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mWebView.loadUrl("file://" + extractPath + "/" + listSite.get(historyIndex));
                hideDialog();
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chm_reader, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);//在菜单中找到对应控件的item
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mWebView.clearMatches();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mWebView.findAllAsync(newText);
                try {
                    for (Method m : WebView.class.getDeclaredMethods()) {
                        if (m.getName().equals("setFindIsUp")) {
                            m.setAccessible(true);
                            m.invoke(mWebView, true);
                            break;
                        }
                    }
                } catch (Exception ignored) {
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
