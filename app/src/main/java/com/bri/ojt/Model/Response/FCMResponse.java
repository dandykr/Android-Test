package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FCMResponse {
    @SerializedName("multicast_id")
    private double multicastId;
    @SerializedName("success")
    private int success;
    @SerializedName("failure")
    private int failure;
    @SerializedName("canonical_ids")
    private int canonicalIds;
    @SerializedName("results")
    private List<MessageId> results;

    public FCMResponse() {
    }

    public double getMulticastId() {
        return multicastId;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailure() {
        return failure;
    }

    public int getCanonicalIds() {
        return canonicalIds;
    }

    public List<MessageId> getResults() {
        return results;
    }

    private class MessageId {
        @SerializedName("message_id")
        private String messageId;

        public MessageId() {
        }

        public String getMessageId() {
            return messageId;
        }
    }
}
