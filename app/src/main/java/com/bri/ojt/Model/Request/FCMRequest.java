package com.bri.ojt.Model.Request;

import com.google.gson.annotations.SerializedName;


/**
 * Send push notification using firebase
 */
public class FCMRequest {
    @SerializedName("to")
    private String toDeviceId;
    @SerializedName("priority")
    private String priority;
    @SerializedName("data")
    private DataPayload dataPayload;

    public FCMRequest() {
    }

    public String getToDeviceId() {
        return toDeviceId;
    }

    public void setToDeviceId(String toDeviceId) {
        this.toDeviceId = toDeviceId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public DataPayload getDataPayload() {
        return dataPayload;
    }

    public void setDataPayload(DataPayload dataPayload) {
        this.dataPayload = dataPayload;
    }

    /**
     *  data payload for notification
     */
    public static class DataPayload {
        @SerializedName("title")
        private String title;
        @SerializedName("body")
        private String body;

        public DataPayload(String title, String body) {
            this.title = title;
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }
    }
}
