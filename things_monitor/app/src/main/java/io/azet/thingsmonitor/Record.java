package io.azet.thingsmonitor;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by azet on 13.12.17.
 */

public class Record extends RealmObject {
    private float value;
    private float number;

    public Record () { }
    public Record (float value, float number) {
        this.value = value;
        this.number = number;
    }
}
