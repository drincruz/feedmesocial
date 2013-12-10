package com.drincruz.service.feedmesocial;

import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;
import java.util.Map;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.auth.AccessToken;

/**
 * Data class representing one tweet
 *
 */
public class TwitterFeedEntry implements FeedEntry {
    // Private
    private Twitter twitter;
    private Map feedData;

    /**
     * Constructor
     *
     * @param Map feedMap
     */
    public TwitterFeedEntry(Map feedMap) {
        feedData = feedMap;
        TwitterToken cToken = TwitterTokenFactory.getChannelToken(feedData.get("twitterUsername").toString());
        twitter = new TwitterFactory().getInstance();
        try {
            twitter.setOAuthConsumer(cToken.consumerKey(),cToken.consumerSecret());
            AccessToken token = new AccessToken(cToken.accessToken(),cToken.accessTokenSecret());
            twitter.setOAuthAccessToken(token);
        }
        catch (IllegalStateException e) {
            System.err.printf("[ERROR] IllegalStateException: %s\n", e.getMessage());
        }
    }

    /**
     * Grab user's tweets
     *
     * @param String user
     * @return ResponseList<Status> tweets
     */
    private ResponseList<Status> getTweets(String user) {
        ResponseList<Status> tweets = null;
        try {
            tweets = twitter.getUserTimeline(user);
        }
        catch (TwitterException e) {
            System.err.printf("[ERROR] TwitterException: %s\n", e.getMessage());
        }
        return tweets;
    }

    /**
     * Iterate and get the latest tweet in the past 24 hours
     *
     * @param ResponseList<Status> tweets
     * @return Status tweet 
     */
    private Status getLastTweetInDay(ResponseList<Status> tweets) {
        ListIterator<Status> li = tweets.listIterator();
        Status tweet = null;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, -1);
        Date yesterday = new Date(now.getTimeInMillis());
        while (li.hasNext()) {
            tweet = li.next();
            if (0 < (tweet.getCreatedAt().compareTo(yesterday))) {
                return tweet;
            }
        }
        return null;
    }

    /**
     * Builds a Twitter permalink for a specified tweet
     * https://twitter.com/KevinDevineTwit/status/365951049820930049
     *
     * @param String username
     * @param long tweetId
     * @return String
     */
    private String buildPermalink(String username, long tweetId) {
        StringBuffer sB = new StringBuffer();
        sB.append("https://twitter.com/");
        sB.append(username);
        sB.append("/status/");
        sB.append(String.valueOf(tweetId));
        return sB.toString();
    }

    /**
     * Gets the data in FeedData format
     *
     * @return FeedData data
     */
    public FeedData getData() {
        ResponseList<Status> tweets = getTweets(feedData.get("twitterUsername").toString());
        if (null == tweets) {
            return null;
        }
        Status tweet = getLastTweetInDay(tweets);
        if (null == tweet) {
            return null;
        }
        FeedData data = new TwitterFeedData.Builder()
            .tweetText(tweet.getText())
            .socialUsername(feedData.get("twitterUsername").toString())
            .socialNetwork("twitter")
            .statusId(tweet.getId())
            .publishDate(tweet.getCreatedAt())
            .permalink(buildPermalink(feedData.get("twitterUsername").toString(), tweet.getId()))
            .build();
        return data;
    }
}
