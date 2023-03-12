package io.azet.thingsmonitor.rest;

import io.azet.thingsmonitor.rest.models.ChannelFeeds;

public interface onResponseListener <E>{
    void onSuccess(E arg);
    void onError();
}
