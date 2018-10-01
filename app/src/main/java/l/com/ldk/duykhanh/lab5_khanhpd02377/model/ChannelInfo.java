package l.com.ldk.duykhanh.lab5_khanhpd02377.model;

public class ChannelInfo {

        private String title, description;
        public ChannelInfo(String title, String description) {
            this.title = title;
            this.description = description;
        }
        public ChannelInfo() {
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

}
