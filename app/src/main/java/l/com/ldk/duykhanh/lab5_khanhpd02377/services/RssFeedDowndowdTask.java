package l.com.ldk.duykhanh.lab5_khanhpd02377.services;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import l.com.ldk.duykhanh.lab5_khanhpd02377.model.ChannelInfo;
import l.com.ldk.duykhanh.lab5_khanhpd02377.model.ChannelItem;
import l.com.ldk.duykhanh.lab5_khanhpd02377.xml.RssFeedParser;

public class RssFeedDowndowdTask extends AsyncTask<String,Void, String> {

    private Context context;
    private WebView webView;

    public RssFeedDowndowdTask(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
    }
    @Override
    protected String doInBackground(String... urls) {
        try {
            return loadXmlFromNetwork(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return e.getMessage();
        }catch(Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }
    @Override
    protected void onPostExecute(String result) {
        webView.loadData(result, "text/html", null);
    }
    private String loadXmlFromNetwork(String urlString) throws
            XmlPullParserException, IOException {
        InputStream stream = null;
        RssFeedParser parser = new RssFeedParser();
        List<ChannelItem> entries = null;
        String title = null;
        String description = null;
        Calendar rightNow = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("MMM dd h:mmaa");
        StringBuilder htmlString = new StringBuilder();
        try {
            Log.d(getClass().getName(), "loadXmlFromNetwork: ");
            stream = downloadUrl(urlString);
            Log.d(getClass().getName(), "loadXmlFromNetwork: Before Parser");
            entries = parser.parse(stream);
            Log.d(getClass().getName(), "loadXmlFromNetwork: After" );
            //display the title and description of channel
            ChannelInfo info = parser.getInfo();
            htmlString.append("<h2>").append(info.getTitle()).append("</h2>");
            htmlString.append("<em>").append(info.getDescription()).append("</em>");
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        for (ChannelItem item: entries) {
            htmlString.append("<p><a href='").append(item.getLink()).append("'>");
            htmlString.append(item.getTitle()).append("</a></p>");
        }
        return htmlString.toString();
    }
    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }

}
