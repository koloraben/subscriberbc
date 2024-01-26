package com.canalplus.subscriberbc.domain.exception;

import java.util.UUID;

public class SubscriberAlreadyInactive extends SubscriberBusinessComponentException {
    public SubscriberAlreadyInactive(UUID subscriberId) {
        super(String.format("Subscriber with ID : %s is already disabled", subscriberId));
    }
}
