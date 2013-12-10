package com.drincruz.service.feedmesocial;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Set Twitter/Google+ users and spawn some threads
 *
 */
public class FeedMeSocialService {
    // Private
    private static final int DEFAULT_THREADS = 2;
    private static final String DEFAULT_OUTPUT_PATH = "/tmp";
    // EDIT these to your usernames
    private static final String TWITTER_USERNAME = "TWITTER_USERNAME";
    private static final String GOOGLE_PLUS_USERNAME = "GOOGLE_PLUS_USERNAME";

    /**
     * Main 
     *
     * @param String[] args
     */
    public static void main(String[] args) {
        String outputPath = DEFAULT_OUTPUT_PATH;
        // Check if any arguments passed in
        if (1 == args.length) {
            outputPath = args[0];
        }
        ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_THREADS);
        // Twitter feed
        // Use a Map to store key/values for Twitter
        Map<String, String> twitterData = new HashMap<String,String>();
        twitterData.put("filePath", outputPath);
        twitterData.put("filename", "twitter");
        twitterData.put("twitterUsername", TWITTER_USERNAME);
        Runnable worker = new FeedMeSocial(twitterData);
        executor.execute(worker);
        // Google+ feed
        // Use a Map to store key/values for Google+
        Map<String, String> googlePlusData = new HashMap<String,String>();
        googlePlusData.put("filePath", outputPath);
        googlePlusData.put("filename", "google_plus");
        googlePlusData.put("googlePlusUsername", GOOGLE_PLUS_USERNAME);
        Runnable worker2 = new FeedMeSocial(googlePlusData);
        executor.execute(worker2);

        // Shutdown the executor
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }
}
