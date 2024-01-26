package com.canalplus.subscriberbc.domain.business;

import com.canalplus.subscriberbc.application.ports.output.SubscriberRepository;
import com.canalplus.subscriberbc.domain.exception.SubscriberBusinessComponentException;
import com.canalplus.subscriberbc.domain.exception.SubscriberNotFoundException;
import com.canalplus.subscriberbc.domain.model.Subscriber;

import java.util.Optional;

public class UpdateSubscriberCommand implements SubscriberCommand {
    private final Subscriber subscriber;
    private final SubscriberRepository subscriberRepository;

    public UpdateSubscriberCommand(Subscriber subscriber, SubscriberRepository subscriberRepository) {
        this.subscriber = subscriber;
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Optional<Subscriber> execute() throws SubscriberBusinessComponentException {
        Optional<Subscriber> existedSubscriber = subscriberRepository.findById(subscriber.getSubscriberId());
        if (existedSubscriber.isEmpty()) {
            throw new SubscriberNotFoundException(subscriber.getSubscriberId());
        }
        return Optional.of(subscriberRepository.save(subscriber));

    }
}
