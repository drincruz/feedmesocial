package com.drincruz.service.feedmesocial;

/**
 * Data class for Google+ Activity
 *
 */
public class GooglePlusFeedData implements FeedData {
    // Builder for GooglePlusFeedData
    public static class Builder {
        // Required variables
        private String activityContent;
        private String activityId;
        private String socialUsername;
        private String socialNetwork;
        private long publishDate;
        private String permalink;
        // Optional
        private String activityUrl;

        public Builder() {}

        public Builder activityContent(String val) {
            activityContent = val;
            return this;
        }

        public Builder activityId(String val) {
            activityId = val;
            return this;
        }

        public Builder activityUrl(String val) {
            activityUrl = val;
            return this;
        }

        public Builder socialUsername(String val) {
            socialUsername = val;
            return this;
        }

        public Builder socialNetwork(String val) {
            socialNetwork = val;
            return this;
        }

        public Builder publishDate(long val) {
            publishDate = val;
            return this;
        }

        public Builder permalink(String val) {
            permalink = val;
            return this;
        }

        public GooglePlusFeedData build() {
            return new GooglePlusFeedData(this);
        }
    }

    // Private variables
    private String text;
    private String socialUsername;
    private String socialNetwork;
    private long publishDate;
    private String permalink;
    private String activityUrl;
    private String activityId;

    /**
     * Constructor
     *
     */
    private GooglePlusFeedData(Builder build) {
        text = build.activityContent;
        socialUsername = build.socialUsername;
        socialNetwork = build.socialNetwork;
        publishDate = build.publishDate;
        permalink = build.permalink;
        activityUrl = build.activityUrl;
        activityId = build.activityId;
    }

    /**
     * Returns the text variable
     *
     * @return String text
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the social username
     *
     * @return String socialUsername
     */
    public String getSocialUsername() {
        return socialUsername;
    }

    /**
     * Returns the social network 
     *
     * @return String network
     */
    public String getSocialNetwork() {
        return socialNetwork;
    }

    /**
     * Returns the publish date
     *
     * @return long publishDate 
     */
    public long getPublishDate() {
        return publishDate;
    }

    /**
     * Returns the permalink
     *
     * @return String permalink
     */
    public String getPermalink() {
        return permalink;
    }

    /**
     * Returns the link (if any) in Activity
     *
     * @return String activityUrl 
     */
    public String getPostLink() {
        return activityUrl;
    }

    /**
     * Returns id of the activity
     *
     * @return String activityId
     */
    public String getPostId() {
        return activityId;
    }
}
