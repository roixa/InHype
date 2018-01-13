package com.roix.inhype.pojo;

import java.util.List;

/**
 * Created by roix on 04.07.2017.
 */

public class GetSubscribersResponse {
    private List<ProfileShort> subscribers;

    public List<ProfileShort> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<ProfileShort> subscribers) {
        this.subscribers = subscribers;
    }
}
