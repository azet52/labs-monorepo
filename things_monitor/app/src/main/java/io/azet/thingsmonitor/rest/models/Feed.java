package io.azet.thingsmonitor.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Feed {

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("entry_id")
    @Expose
    private Integer entryId;

    @SerializedName("field1")
    @Expose
    private String field1;

    @SerializedName("field2")
    @Expose
    private String field2;

    @SerializedName("field3")
    @Expose
    private String field3;

    @SerializedName("field4")
    @Expose
    private String field4;

    @SerializedName("field5")
    @Expose
    private String field5;

    @SerializedName("field6")
    @Expose
    private String field6;

    @SerializedName("field7")
    @Expose
    private String field7;

    @SerializedName("field8")
    @Expose
    private String field8;


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getEntryId() {
        return entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }


    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    public String getField6() {
        return field6;
    }

    public void setField6(String field6) {
        this.field6 = field6;
    }

    public String getField7() {
        return field7;
    }

    public void setField7(String field7) {
        this.field7 = field7;
    }

    public String getField8() {
        return field8;
    }

    public void setField8(String field8) {
        this.field8 = field8;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("createdAt", createdAt).append("entryId", entryId).append("field1", field1).append("field2", field2).append("field3", field3).toString();
    }

}
