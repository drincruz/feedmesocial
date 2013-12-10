package com.drincruz.service.feedmesocial;

/**
 * Provides a data object for a channel's twitter token data
 *
 */
public class TwitterToken {
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;

    /**
     * Constructor
     *
     */
    public TwitterToken(String ck, String cs, String at, String ats) {
        consumerKey = ck;
        consumerSecret = cs;
        accessToken = at;
        accessTokenSecret = ats;
    }

    /**
     * Gets the consumerKey
     *
     * @return String consumerKey
     */
    public String consumerKey() {
        return consumerKey;
    }

    /**
     * Gets the consumerSecret
     *
     * @return String consumerSecret
     */
    public String consumerSecret() {
        return consumerSecret;
    }

    /**
     * Gets the accessToken
     *
     * @return String accessToken
     */
    public String accessToken() {
        return accessToken;
    }

    /**
     * Gets the accessTokenSecret
     *
     * @return String accessTokenSecret
     */
    public String accessTokenSecret() {
        return accessTokenSecret;
    }
}
