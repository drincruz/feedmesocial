package com.drincruz.service.feedmesocial;

import com.drincruz.service.feedmesocial.io.FeedWriter;
import com.drincruz.service.feedmesocial.util.TextUtil;
import java.util.Map;
import twitter4j.Status;
import twitter4j.ResponseList;
import twitter4j.RateLimitStatus;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * Threads the Social feeds
 *
 * @see Runnable http://docs.oracle.com/javase/1.4.2/docs/api/java/lang/Runnable.html
 */
public class FeedMeSocial implements Runnable {
    // Private
    private Map feedData;

    /**
     * Constructor
     *
     * @param Map data 
     */
    public FeedMeSocial(Map data) {
        feedData = data;
    }

    /**
     * run()
     *
     * @return void
     */
    public void run() {
        FeedEntry twitterFeedEntry = null;
        FeedEntry googlePlusFeedEntry = null;
        FeedData feed = null;

        // Check for Google+ posts if Google+ username is present
        if (null != feedData.get("googlePlusUsername")) {
            googlePlusFeedEntry = new GooglePlusFeedEntry(feedData);
            if (null != googlePlusFeedEntry.getData()) {
                feed = googlePlusFeedEntry.getData();
            }
        }
        // Try Twitter if available
        if ( (null == feed) && (null != feedData.get("twitterUsername")) ) {
            twitterFeedEntry = new TwitterFeedEntry(feedData);
            if (null != twitterFeedEntry.getData()) {
                feed = twitterFeedEntry.getData();
            }
        }
        // If feed is _still_ null, return; nothing to do
        if (null == feed) {
            return;
        }
        FeedWriter out = null;
        FeedWriter home = null;
        if (null != feedData.get("filePath").toString()) {
            out = new FeedWriter(feedData.get("filename").toString(),feedData.get("filePath").toString());
        }
        else {
            out = new FeedWriter(feedData.get("filename").toString());
        }
        try {
            String j = null;
            // If no text, nothing to write
            if (null == feed.getText()) {
                return;
            }
            out.initJsonFile();
            j = out.readFile();
            String safeFeedText = StringEscapeUtils.unescapeHtml(
                StringEscapeUtils.unescapeXml(feed.getText())
            );
            safeFeedText = TextUtil.shorten(safeFeedText);

            JSONArray a = out.toJsonArray(j);
            JSONObject json = new JSONObject();
            json.put("screen_name", feed.getSocialUsername());
            json.put("text", StringEscapeUtils.escapeHtml(safeFeedText));
            json.put("social_network", feed.getSocialNetwork());
            json.put("permalink", feed.getPermalink());
            json.put("post_link", feed.getPostLink());
            json.put("post_id", feed.getPostId());
            a.add(json);
            out.writeFile(a.toJSONString());
            System.out.printf("[INFO] %s/%s.json,%s,%s\n", feedData.get("filePath").toString(),feedData.get("filename").toString(),
                feed.getSocialUsername(),feed.getText()
            );
        }
        catch (IOException e) {
            System.err.printf("[ERROR] IOException e: %s\n", e.getMessage());
        }
    }
}
