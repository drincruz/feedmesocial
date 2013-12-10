package com.drincruz.service.feedmesocial;

import java.util.Date;

/**
 * Data class for a tweet
 *
 */
public class TwitterFeedData implements FeedData {
    // Builder for TwitterFeedData
    public static class Builder {
        // Required variables
        private String tweetText;
        private String socialUsername;
        private String socialNetwork;
        private Date publishDate;
        private String permalink;
        private long statusId;

        public Builder() {}

        public Builder tweetText(String val) {
            tweetText = val;
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

        public Builder statusId(long val) {
            statusId = val;
            return this;
        }

        public Builder publishDate(Date val) {
            publishDate = val;
            return this;
        }

        public Builder permalink(String val) {
            permalink = val;
            return this;
        }

        public TwitterFeedData build() {
            return new TwitterFeedData(this);
        }
    }

    // Private variables
    private String text;
    private String socialUsername;
    private String socialNetwork;
    private long statusId;
    private Date publishDate;
    private String permalink;

    /**
     * Constructor
     *
     */
    private TwitterFeedData(Builder build) {
        text = build.tweetText;
        socialUsername = build.socialUsername;
        socialNetwork = build.socialNetwork;
        statusId = build.statusId;
        publishDate = build.publishDate;
        permalink = build.permalink;
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
     * @return Date publishDate 
     */
    public Date getPublishDate() {
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
     * Returns null
     * Twitter does not have a link data object 
     * outside of searching for one in the Status text manually
     *
     * @return null
     */
    public String getPostLink() {
        return null;
    }

    /**
     * Returns the id of a Status
     *
     * @return String 
     */
    public String getPostId() {
        return String.valueOf(statusId);
    }
}
