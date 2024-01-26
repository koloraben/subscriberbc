package com.canalplus.subscriberbc.domain.business;

import com.canalplus.subscriberbc.domain.exception.SubscriberBusinessComponentException;
import com.canalplus.subscriberbc.domain.model.Subscriber;

import java.util.Optional;

public interface SubscriberCommand {
    Optional<Subscriber> execute() throws SubscriberBusinessComponentException;

}
