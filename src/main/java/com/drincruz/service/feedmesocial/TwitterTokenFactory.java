package com.drincruz.service.feedmesocial;

/**
 * Provides a factory to get a TwitterToken instance for specific channel
 * In case of multiple Twitter accounts I created a Factory.
 *
 */
public class TwitterTokenFactory {
    /**
     * Return TwitterToken
     *
     * @param String username 
     * @return TwitterToken instance
     */
    public static TwitterToken getChannelToken(String username) {
        // Edit this with your Twitter username
        // Feel free to add more than one Twitter username in case you have multiple.
        if (username.equals("YOUR_USER_NAME")) {
            return new TwitterToken(
                // Add your Twitter token information here
                "CONSUMER_KEY",
                "CONSUMER_SECRET",
                "ACCESS_TOKEN",
                "ACCESS_TOKEN_SECRET"
            );
        }
        else {
            return null;
        }
    }
}
