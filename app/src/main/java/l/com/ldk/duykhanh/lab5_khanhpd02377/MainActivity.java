package l.com.ldk.duykhanh.lab5_khanhpd02377;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import l.com.ldk.duykhanh.lab5_khanhpd02377.services.RssFeedDowndowdTask;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private RssFeedDowndowdTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.web);

        task=new RssFeedDowndowdTask(this,mWebView);
        task.execute("https://www.tienphong.vn/rss/can-tho-293.rss");
    }
}
