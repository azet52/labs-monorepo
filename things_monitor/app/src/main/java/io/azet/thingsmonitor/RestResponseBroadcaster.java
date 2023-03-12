package io.azet.thingsmonitor;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import io.azet.thingsmonitor.fragments.SettingsFragment;
import io.azet.thingsmonitor.rest.WebServices;
import io.azet.thingsmonitor.rest.models.Channel;
import io.azet.thingsmonitor.rest.models.ChannelFeeds;
import io.azet.thingsmonitor.rest.models.Feed;
import io.azet.thingsmonitor.rest.onResponseListener;

import static android.util.Log.d;

/**
 * Created by azet on 12.12.17.
 */

public class RestResponseBroadcaster extends Service {

    private static final String DOWNLOAD_UPDATE = "azet.io.settings.download.update";
    private static final String NOTIFICATION_UPDATE = "azet.io.settings.notifcation.update";
    private static final String NEW_FEED = "azet.io.feed.update";
    private static final String BAD_CHANNEL = "azet.io.feed.error";

    WebServices webServices;

    private Feed saved = null;
    private Channel newChannel = null;

    private String access = null;
    private String channel = null;
    private String apiKey = null;

    private boolean enabled = false;
    private String field = null;
    private float threshold;
    private String action = null;

    private String[] fieldNames = new String[8];
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (channel != null) {
                        if (access.equals("private")) {
                            webServices.getPrivateChannelFeeds(new onResponseListener<ChannelFeeds>() {
                                @Override
                                public void onSuccess(ChannelFeeds channelFeeds) {
                                    newChannel = channelFeeds.getChannel();
                                    Feed feed = channelFeeds.getFeeds().get(0);
                                    if (saved == null) {
                                        saved = feed;
                                        newFeed();
                                    } else if (saved.getCreatedAt().equals(feed.getCreatedAt())) {
                                        saved = feed;
                                        newFeed();
                                    }
                                }

                                @Override
                                public void onError() {
                                    Intent intent = new Intent();
                                    badChannel(intent);

                                }
                            }, channel, apiKey, 1);
                        } else {
                            webServices.getPublicChannelFeeds(new onResponseListener<ChannelFeeds>() {
                                @Override
                                public void onSuccess(ChannelFeeds channelFeeds) {
                                    newChannel = channelFeeds.getChannel();
                                    Feed feed = channelFeeds.getFeeds().get(0);
                                    if (saved == null) {
                                        saved = feed;
                                        newFeed();
                                    } else if (saved.getCreatedAt().equals(feed.getCreatedAt())) {
                                        saved = feed;
                                        newFeed();
                                    }
                                }

                                @Override
                                public void onError() {
                                    Intent intent = new Intent();
                                    badChannel(intent);
                                }
                            }, channel, 1);

                        }

                    }

                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }}).start();
        return START_REDELIVER_INTENT;
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(DOWNLOAD_UPDATE)) {
                readDownloadUpdate(intent);
            } else if (action.equals(NOTIFICATION_UPDATE)) {
                Log.d("NOTIFY-UPDATE", "YES");
                readNotificationUpdate(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DOWNLOAD_UPDATE);
        filter.addAction(NOTIFICATION_UPDATE);

        registerReceiver(receiver, filter);

        webServices = WebServices.getInstance();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
    }

    private boolean readDownloadUpdate(Intent intent) {
        this.access = intent.getExtras().getString("access");
        this.channel = intent.getExtras().getString("channel");
        if (this.access.equals("private")) {
            this.apiKey = intent.getExtras().getString("apiKey");
        }
        return (channel != null && apiKey != null);
    }

    private boolean readNotificationUpdate(Intent intent) {

        this.enabled = intent.getExtras().getBoolean("enabled");
        this.field = intent.getExtras().getString("field");
        this.threshold =intent.getExtras().getFloat("threshold");
        this.action = intent.getExtras().getString("action");

        Log.d("NOTIFY-PARAMS", "field: " + field + ", threshold: " + String.valueOf(threshold) + ", action: " + action);
        return (field != null && action != null);
    }

    private boolean checkIfFullfill(String value, float threshold, String action) {
        Log.d("Service", value + action + String.valueOf(threshold));
        if (action.equals("Above")){
            return (Float.valueOf(value) > threshold);
        } else if (action.equals("Below")) {
            return (Float.valueOf(value) < threshold);
        } else if (action.equals("Exact")) {
            return (Float.valueOf(value) == threshold);
        }
        return false;
    }

    private void newFeed () {
        if (enabled) {
            Log.d("Service enabled", String.valueOf(enabled));
            boolean condition = false;
            if (field.equals("1")) {
                condition = checkIfFullfill(saved.getField1(), threshold, action);
            } else if (field.equals("2") && !condition) {
                condition = checkIfFullfill(saved.getField2(), threshold, action);
            } else if (field.equals("3") && !condition) {
                condition = checkIfFullfill(saved.getField3(), threshold, action);
            } else if (field.equals("4") && !condition) {
                condition = checkIfFullfill(saved.getField4(), threshold, action);
            } else if (field.equals("5") && !condition) {
                condition = checkIfFullfill(saved.getField5(), threshold, action);
            } else if (field.equals("6") && !condition) {
                condition = checkIfFullfill(saved.getField6(), threshold, action);
            } else if (field.equals("7") && !condition) {
                condition = checkIfFullfill(saved.getField7(), threshold, action);
            } else if (field.equals("8") && !condition) {
                condition = checkIfFullfill(saved.getField8(), threshold, action);
            }

            Log.d("Service condition",  String.valueOf(condition));

            if (condition) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "things_monitor");
                mBuilder.setSmallIcon(R.drawable.exclamation)
                        .setContentTitle("Things Monitor")
                        .setContentText("Field" + field + " is " + action.toLowerCase() + " " + threshold);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());
            }
        }

        Intent intent = new Intent();
        intent.setAction(NEW_FEED);
        Log.d("WebService feed", newChannel.getField1() + ": " + saved.getField1());

        intent.putExtra("nameField1", newChannel.getField1());
        intent.putExtra("nameField2", newChannel.getField2());
        intent.putExtra("nameField3", newChannel.getField3());
        intent.putExtra("nameField4", newChannel.getField4());
        intent.putExtra("nameField5", newChannel.getField5());
        intent.putExtra("nameField6", newChannel.getField6());
        intent.putExtra("nameField7", newChannel.getField7());
        intent.putExtra("nameField8", newChannel.getField8());

        intent.putExtra("date", saved.getCreatedAt());
        intent.putExtra("field1", saved.getField1());
        intent.putExtra("field2", saved.getField2());
        intent.putExtra("field3", saved.getField3());
        intent.putExtra("field4", saved.getField4());
        intent.putExtra("field5", saved.getField5());
        intent.putExtra("field6", saved.getField6());
        intent.putExtra("field7", saved.getField7());
        intent.putExtra("field8", saved.getField8());
        Log.d("service", "new feed");
        sendBroadcast(intent);

    }

    private void badChannel (Intent intent) {
        intent.setAction(BAD_CHANNEL);
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
