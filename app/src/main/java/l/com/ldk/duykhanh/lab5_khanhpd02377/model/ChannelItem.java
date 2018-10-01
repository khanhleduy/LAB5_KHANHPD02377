package l.com.ldk.duykhanh.lab5_khanhpd02377.model;

public class ChannelItem {

    private String title, description, pubDate, link, guid;
    public ChannelItem() {
    }
    public ChannelItem(String title, String description, String pubDate, String
            link, String guid) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.guid = guid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPubDate() {
        return pubDate;
    }
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getGuid() {
        return guid;
    }
    public void setGuid(String guid) {
        this.guid = guid;
    }
}
