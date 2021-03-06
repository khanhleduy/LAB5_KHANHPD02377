package l.com.ldk.duykhanh.lab5_khanhpd02377.xml;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import l.com.ldk.duykhanh.lab5_khanhpd02377.model.ChannelInfo;
import l.com.ldk.duykhanh.lab5_khanhpd02377.model.ChannelItem;

public class RssFeedParser {
    private static final String ns = null;
    private ChannelInfo info;
    public List<ChannelItem> parse(InputStream in) throws XmlPullParserException,
            IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
// return readChannel(parser);
            return readRss(parser);
        } finally {
            in.close();
        }
    }
    private List<ChannelItem> readRss(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        List<ChannelItem> entries = new ArrayList<>();
        parser.require(XmlPullParser.START_TAG, ns, "rss");
        parser.nextTag();
        entries = readChannel(parser);
        Log.d(getClass().getName(), "readRss: " + parser.getName());
        parser.nextTag();
        parser.require(XmlPullParser.END_TAG, ns, "rss");
        return entries;
    }
    private List<ChannelItem> readChannel(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        List<ChannelItem> entries = new ArrayList<>();
        String title = null;

        String description = null;
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("item")) {
                entries.add(readItem(parser));
            } else if (name.equals("title")) {
                title = readText(parser);
            } else if (name.equals("description")) {
                description = readDescription(parser);
            } else {
                skip(parser);
            }
        }
        info = new ChannelInfo(title, description);
        return entries;
    }
    private ChannelItem readItem(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null, description = null, link = null, guid = null, pubDate =
                null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("description")) {
                description = readDescription(parser);
            } else if (name.equals("pubDate")) {
                pubDate = readPubDate(parser);
            } else if (name.equals("link")) {
                link = readLink(parser);
            } else if (name.equals("guid")) {
                guid = readGuid(parser);
            }else {
                skip(parser);
            }
        }
        return new ChannelItem(title,description,pubDate,link,guid);
    }
    private String readGuid(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "guid");
        String guid = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "guid");
        return guid;
    }
    private String readPubDate(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");

        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return pubDate;
    }
    private String readDescription(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }
    // Processes title tags in the feed.
    private String readTitle(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }
    // Processes link tags in the feed.
    private String readLink(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }
    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException,
            XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException,
            IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
    public ChannelInfo getInfo() {
        return info;
    }
    public void setInfo(ChannelInfo info) {
        this.info = info;
    }
}
