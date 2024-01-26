package com.canalplus.subscriberbc.domain.business;

import com.canalplus.subscriberbc.application.ports.output.SubscriberRepository;
import com.canalplus.subscriberbc.domain.exception.SubscriberBusinessComponentException;
import com.canalplus.subscriberbc.domain.exception.SubscriberNotFoundException;
import com.canalplus.subscriberbc.domain.model.Subscriber;

import java.util.Optional;
import java.util.UUID;

public class ReadSubscriberCommand implements SubscriberCommand {
    private final UUID id;
    private final SubscriberRepository subscriberRepository;

    public ReadSubscriberCommand(UUID id, SubscriberRepository subscriberRepository) {
        this.id = id;
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Optional<Subscriber> execute() throws SubscriberBusinessComponentException {
        Optional<Subscriber> subscriber = subscriberRepository.findById(id);
        if (subscriber.isEmpty()) {
            throw new SubscriberNotFoundException(id);
        }
        return subscriber;
    }
}
