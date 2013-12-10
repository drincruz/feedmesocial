package com.drincruz.service.feedmesocial;

/**
 * A data interface representing one single feed entry of a social network
 *
 */
public interface FeedData {
    /**
     * Return the text from a post
     * 
     * @return String
     */
    String getText();

    /**
     * Return the username/id from social network
     * 
     * @return String
     */
    String getSocialUsername();

    /**
     * Return the social network
     * 
     * @return String
     */
    String getSocialNetwork();

    /**
     * Return the permalink for the post/tweet
     * 
     * @return String
     */
    String getPermalink();

    /**
     * Return the link (if any) in the post/tweet
     *
     * @return String
     * @since 2.3.2
     */
    String getPostLink();

    /**
     * Return the id of the post/tweet
     *
     * @return String
     * @since 2.3.3
     */
    String getPostId();
}
