package com.canalplus.subscriberbc.domain.business;

import com.canalplus.subscriberbc.application.ports.output.SubscriberRepository;
import com.canalplus.subscriberbc.domain.exception.SubscriberAlreadyInactive;
import com.canalplus.subscriberbc.domain.exception.SubscriberBusinessComponentException;
import com.canalplus.subscriberbc.domain.exception.SubscriberNotFoundException;
import com.canalplus.subscriberbc.domain.model.Subscriber;

import java.util.Optional;
import java.util.UUID;

public class CancelSubscriptionCommand implements SubscriberCommand {
    private final UUID id;
    private final SubscriberRepository subscriberRepository;

    public CancelSubscriptionCommand(UUID id, SubscriberRepository subscriberRepository) {
        this.id = id;
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Optional<Subscriber> execute() throws SubscriberBusinessComponentException {
        Optional<Subscriber> optionalExistedSubscriber = subscriberRepository.findById(id);
        if (optionalExistedSubscriber.isEmpty()) {
            throw new SubscriberNotFoundException(id);
        }
        Subscriber existedSubscriber = optionalExistedSubscriber.get();
        if (!existedSubscriber.getIsActive()) {
            throw new SubscriberAlreadyInactive(id);
        }
        existedSubscriber.setActive(false);
        return Optional.of(subscriberRepository.save(existedSubscriber));

    }
}
