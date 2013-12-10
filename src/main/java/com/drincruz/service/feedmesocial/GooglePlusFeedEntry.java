package com.drincruz.service.feedmesocial;

import java.util.Map;
import java.io.IOException;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.plus.Plus;
import com.google.common.io.Files;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.ActivityFeed;
import org.jsoup.Jsoup;

/**
 * Data class for Google+ feed entry
 *
 * @since 2.3
 */
public class GooglePlusFeedEntry implements FeedEntry {
    // Private variables
    private Map feedData;
    private static HttpTransport HTTP_TRANSPORT;
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static Plus plus;
    private static final String APPLICATION_NAME = "DrincruzDotCom-GooglePlusFeed/2.4";
    // Set up your Google API service account project and set the following variables
    private static final String SERVICE_ACCOUNT_EMAIL = "EMAIL_FROM_GOOGLE_API_CONSOLE@developer.gserviceaccount.com";
    private static final String GOOGLE_API_KEY = "/LOCATION_OF_YOUR/key.p12";

    /**
     * Constructor
     *
     */
    public GooglePlusFeedEntry(Map data) {
        feedData = data;
    }
    
    /**
     * Grabs public activities for Google+ user with googlePlusId
     *
     * @param String googlePlusId
     * @return List<Activity>
     */
    private List<Activity> getActivities(String googlePlusId) {
        try {
            try {
                HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
                File keyFile = new File(GOOGLE_API_KEY);
                if (!keyFile.exists()) {
                    System.err.printf("[ERROR] File does not exist: %s\n", GOOGLE_API_KEY);
                    return null;
                }
                // service account credential (uncomment setServiceAccountUser for domain-wide delegation)
                GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
                    .setJsonFactory(JSON_FACTORY)
                    .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                    .setServiceAccountScopes(Collections.singleton(PlusScopes.PLUS_ME))
                    .setServiceAccountPrivateKeyFromP12File(new File(GOOGLE_API_KEY))
                    .build();
                // set up global Plus instance
                plus = new Plus.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME).build();
                // run commands
                Plus.Activities.List listActivities = plus.activities().list(googlePlusId, "public");
                listActivities.setMaxResults(5L);
                ActivityFeed activityFeed = listActivities.execute();
                return activityFeed.getItems();
            }
            catch (IOException e) {
                System.err.printf("[ERROR] %s\n", e.getMessage());
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
 
    /**
     * Grabs the latest activity from a List<Activity> within the last day
     *
     * @param List<Activity> activities
     * @return Activity activity
     */ 
    private static Activity getLastActivityInDay(List<Activity> activities) {
        ListIterator<Activity> li = activities.listIterator();
        Activity activity = null;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, -1);
        while (li.hasNext()) {
            activity = li.next();
            if ( (activity.getPublished().getValue() > now.getTimeInMillis())
                && (null != activity.getObject().getContent())
                && (0 <  activity.getObject().getContent().length())
            ) {
                return activity;
            }
        }
        return null;
    }

    /**
     * Get the url of attachment type "article"
     *
     * @param List<Activity.PlusObject.Attachments attachments
     * @return String 
     */
    private static String getAttachmentUrl(List<Activity.PlusObject.Attachments> attachments) {
        if (0 == attachments.size()) {
            return null;
        }
        for (Activity.PlusObject.Attachments attachment: attachments) {
            if ( (attachment.getObjectType().equals("article")) 
                || (attachment.getObjectType().equals("photo"))
            ) {
                return attachment.getUrl();
            }
        }
        return null;
    }

    /**
     * Gets the data in FeedData format
     *
     * @return FeedData data
     */
    public FeedData getData() {
        List<Activity> activities = getActivities(feedData.get("googlePlusUsername").toString());
        if (null == activities) {
            return null;
        }
        Activity activity = getLastActivityInDay(activities);
        if (null == activity) {
            return null;
        }
        FeedData data = new GooglePlusFeedData.Builder()
            .activityContent(Jsoup.parse(activity.getObject().getContent()).text())
            .activityUrl(getAttachmentUrl(activity.getObject().getAttachments()))
            .activityId(activity.getId())
            .socialUsername(feedData.get("googlePlusUsername").toString())
            .socialNetwork("googlePlus")
            .publishDate(activity.getPublished().getValue())
            .permalink(activity.getUrl())
            .build();
        return data;
    }
}
