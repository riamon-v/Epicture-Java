package com.example.riamon_v.java_epicture_2017.SignLoginHandling;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*        import com.illuminatek.epitech.epicture.Database.User;
        import com.illuminatek.epitech.epicture.Database.UserDatabase;
        import com.illuminatek.epitech.epicture.R;*/

import com.example.riamon_v.java_epicture_2017.DatabaseManagment.DatabaseHandler;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;
import com.example.riamon_v.java_epicture_2017.MainActivity;
import com.example.riamon_v.java_epicture_2017.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImgurLoginActivity extends AppCompatActivity {

    WebView mWebView;
    String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_imgur);

        mWebView = (WebView) findViewById(R.id.webview);
        String url = getString(R.string.api_imgur) +
                "oauth2/authorize?client_id=" + getString(R.string.CLIENT_ID_IMGUR) +
                "&response_type=token";

        connect(mWebView, url);
    }

    public boolean connect(WebView mWebView, final String mUrl) {

        /*String turl = R.string.instagram_api_url +
                "/oauth/authorize/?client_id=" + R.string.instagram_id + "&redirect_uri=https://handle.epicture.com" +
                "&response_type=token"; */
        mWebView.setWebViewClient(new WebViewClient()
        {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url)
            {
                return shouldOverrideUrlLoading(url);
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request)
            {
                Uri uri = request.getUrl();
                return shouldOverrideUrlLoading(uri.toString());
            }

            private boolean shouldOverrideUrlLoading(final String url)
            {

                Pattern p = Pattern.compile("(?<=access_token=).*?(?=&|$)");
                Matcher m = p.matcher(url);

                Log.i("rttt", url);

                finish();
               /* User user = DatabaseHandler.getInstance(getApplicationContext()).getUserDao().
                        findById(Integer.toString(getIntent().getIntExtra("idUser", 0)));*/
                if (m.find()) {
                    User user = new User("", "loginImgur", "passImgur");
                    user.setTokenImgur(m.group());
                    DatabaseHandler.getInstance(getApplicationContext()).getUserDao().insertUser(user);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                return true; // Returning True means that application wants to leave the current WebView and handle the url itself, otherwise return false.
            }
        });
        mWebView.loadUrl(mUrl);
        return true;
    }

        /*

        mWebView.setWebViewClient(new WebViewClient()
        {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url)
            {
                return shouldOverrideUrlLoading(url);
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request)
            {
                Uri uri = request.getUrl();
                return shouldOverrideUrlLoading(uri.toString());
            }

            private boolean shouldOverrideUrlLoading(final String url)
            {

                Pattern p = Pattern.compile("(?<=access_token=).*?(?=&|$)");
                Matcher m = p.matcher(url);

                return true; // Returning True means that application wants to leave the current WebView and handle the url itself, otherwise return false.
            }
        });
        mWebView.loadUrl(url);
    }*/

}

