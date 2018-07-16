package com.example.xiaoniu.publicuseproject.https;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.xiaoniu.publicuseproject.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;

import org.apache.http.Header;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;

import java.security.KeyStore;


public class HttpsActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https_request);

        mTextView = (TextView) findViewById(R.id.tv);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getAllGamesServer() {
//        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();
        AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient(getSchemeRegistry(false, 80, 443));
        mAsyncHttpClient.setTimeout(7000);
        mAsyncHttpClient.get("https://pub.gamezop.com/v3/games?id=S1_l_hn4YJQ&lang=en", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("diykeyboard", "AllGames onSuccess: " + statusCode + ", " + new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("diykeyboard", "AllGames onFailure: " + statusCode + ", " + (responseBody != null ? new String(responseBody):null) + ", " + error);
            }
        });
    }

    private SchemeRegistry getSchemeRegistry(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        if(fixNoHttpResponseException) {
            Log.d("AsyncHttpClient", "Beware! Using the fix is insecure, as it doesn\'t verify SSL certificates.");
        }

        if(httpPort < 1) {
            httpPort = 80;
            Log.d("AsyncHttpClient", "Invalid HTTP port number specified, defaulting to 80");
        }

        if(httpsPort < 1) {
            httpsPort = 443;
            Log.d("AsyncHttpClient", "Invalid HTTPS port number specified, defaulting to 443");
        }

        SSLSocketFactory sslSocketFactory;
        if(fixNoHttpResponseException) {
            sslSocketFactory = MySSLSocketFactory.getFixedSocketFactory();
        } else {
            sslSocketFactory = SSLSocketFactory.getSocketFactory();
        }

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), httpPort));
        schemeRegistry.register(new Scheme("https", sslSocketFactory, httpsPort));

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            schemeRegistry.register(new Scheme("https", sslSocketFactory,
                    httpsPort));
            schemeRegistry.register(new Scheme("https",sf, httpsPort));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return schemeRegistry;
    }
}
