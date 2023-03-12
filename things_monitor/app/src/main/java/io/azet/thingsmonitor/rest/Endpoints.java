package io.azet.thingsmonitor.rest;

import io.azet.thingsmonitor.rest.models.ChannelFeeds;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by azet on 12.12.17.
 */

public interface Endpoints {

    // /84008/feeds.json?results=2
    // /378896/feeds.json?api_key=9JEUQ7J9PRP2E4L8&results=2

    @GET("/channels/{channel}/feed.json")
    Call<ChannelFeeds> getPublicChannelFeeds(@Path("channel") String channel,
                                             @Query("results") int results);

    @GET("/channels/{channel}/feed.json")
    Call<ChannelFeeds> getPrivateChannelFeeds(@Path("channel") String channel,
                                              @Query("api_key") String key,
                                              @Query("results") int results );

    //data fromat YYYY-MM-DD%20HH:NN:SS

    @GET("/channels/{channel}/feed.json")
    Call<ChannelFeeds> getPublicChannelRangeFeeds(@Path("channel") String channel,
                                             @Query("results") int results,
                                                  @Query("start") String start,
                                                  @Query("end") String end);

    @GET("/channels/{channel}/feed.json")
    Call<ChannelFeeds> getPrivateChannelRangeFeeds(@Path("channel") String channel,
                                              @Query("api_key") String key,
                                              @Query("results") int results,
                                                   @Query("start") String start,
                                                   @Query("end") String end);

}
