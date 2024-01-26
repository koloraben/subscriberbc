package com.canalplus.subscriberbc.domain.exception;

import java.util.UUID;

public class SubscriberNotFoundException extends SubscriberBusinessComponentException {

    public SubscriberNotFoundException(UUID subscriberId) {
        super(String.format("Subscriber with ID : %s is not found", subscriberId));
    }
}
