package app.com.example.heeyoung.artsshow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/* 작품등록 연결 페이지*/
public class ArtsRegisterActivity extends Activity {
    WebView mWebView;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arts_register);

        mWebView = (WebView) findViewById(R.id.arts_register_web);

        mWebView.getSettings().setJavaScriptEnabled(true);  //자바스크립트 허용
        mWebView.setVerticalScrollbarOverlay(true);        // 스크롤 공백 없애기
        mWebView.loadUrl("http://engca.co.kr/habit/listview2.php");  //임시URL..

    }


    @Override

    protected void onDestroy() {

        if (mWebView != null)

        {
            mWebView.destroyDrawingCache();
            mWebView.destroy();
        }

        super.onDestroy();

    }
}