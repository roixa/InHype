package com.roix.inhype.pojo;

import java.util.List;

/**
 * Created by roix on 04.07.2017.
 */

public class GetSubscriptionsResponse {

    private List<ProfileShort> subscriptions;

    public List<ProfileShort> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<ProfileShort> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
