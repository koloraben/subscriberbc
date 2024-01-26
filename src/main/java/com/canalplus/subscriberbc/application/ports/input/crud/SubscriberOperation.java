package com.canalplus.subscriberbc.application.ports.input.crud;


import com.canalplus.subscriberbc.domain.exception.SubscriberAlreadyExistException;
import com.canalplus.subscriberbc.domain.exception.SubscriberNotFoundException;
import com.canalplus.subscriberbc.domain.model.Subscriber;

import java.util.Optional;
import java.util.UUID;

public interface SubscriberOperation {

    Optional<Subscriber> create(Subscriber subscriber) throws SubscriberAlreadyExistException;

    Optional<Subscriber> read(UUID id) throws SubscriberNotFoundException;

    Optional<Subscriber> update(Subscriber subscriber);

    Optional<Subscriber> cancelSubscription(UUID id);
}
