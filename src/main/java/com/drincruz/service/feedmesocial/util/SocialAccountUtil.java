package com.drincruz.service.feedmesocial.util;

/**
 * Utility class for useful social account methods
 *
 * @since 2.3
 */
public class SocialAccountUtil {
    /**
     * Get Twitter username from twitter url
     *
     * @param String url
     * @return String url
     */
    public static String twitterUsernameFromUrl(String url) {
        if (null == url) {
            return null;
        }
        String re = "(http)?s?(://)?(www.)?twitter.com/(#!/)?";
        url = url.replaceAll(re, "");
        return url;
    }

    /**
     * Get the Google+ ID from a plus.google.com url
     * https://plus.google.com/118064008740771173119
     *
     * @param String url
     * @return String url
     */
    public static String googlePlusIdFromUrl(String url) {
        if (null == url) {
            return null;
        }
        String re = "(http)?s?(://)?(plus.)?google.com/?";
        url = url.replaceAll(re, "");
        return url;
    }
}
