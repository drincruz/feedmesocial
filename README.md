feedmesocial
============

A simple Google+ / Twitter tool to pull down posts.

This project pulls down the (one) last post from a Google+ and Twitter account. By default, we write to a small .json text file. Though, this can easily be modified to just handle the data without writing to a file.


How to Get Started
----------
If you want to just use the service "as is", you'll just need to edit the following files:
  FeedMeSocialService.java
  GooglePlusFeedEntry.java
  TwitterTokenFactory.java
    
The changes necessary should be self-explanatory, enter your Google+ / Twitter user IDs. Also, don't forget to create the oAuth tokens for each respective network. You will need to get a Twitter token by creating an app at dev.twitter.com. You will also need to create a project with a service account for Google+ on cloud.google.com/console. On the Google API console, you will be able to download the service key as well as get the email address for the service account used in `GooglePlusFeedEntry.java`
