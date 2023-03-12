package io.azet.thingsmonitor.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ChannelFeeds {

    @SerializedName("channel")
    @Expose
    private Channel channel;

    @SerializedName("feeds")
    @Expose
    private List<Feed> feeds = null;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("channel", channel).append("feeds", feeds).toString();
    }
}
