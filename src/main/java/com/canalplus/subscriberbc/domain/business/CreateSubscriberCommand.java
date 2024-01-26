package com.canalplus.subscriberbc.domain.business;

import com.canalplus.subscriberbc.application.ports.output.SubscriberRepository;
import com.canalplus.subscriberbc.domain.exception.SubscriberAlreadyExistException;
import com.canalplus.subscriberbc.domain.exception.SubscriberBusinessComponentException;
import com.canalplus.subscriberbc.domain.model.Subscriber;

import java.util.Optional;
import java.util.UUID;

public class CreateSubscriberCommand implements SubscriberCommand {
    private final Subscriber subscriber;
    private final SubscriberRepository subscriberRepository;

    public CreateSubscriberCommand(Subscriber subscriber, SubscriberRepository subscriberRepository) {
        this.subscriber = subscriber;
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Optional<Subscriber> execute() throws SubscriberBusinessComponentException {
        Optional<Subscriber> existedSubscriber = subscriberRepository.findByEmailOrPhone(subscriber.getMail(), subscriber.getPhone());
        existedSubscriber.ifPresent(es -> {
            if (es.getMail().equalsIgnoreCase(subscriber.getMail())) {
                throw new SubscriberAlreadyExistException(String.format("Subscriber exists with email: %s", subscriber.getMail()));
            }
            if (es.getPhone().equalsIgnoreCase(subscriber.getPhone())) {
                throw new SubscriberAlreadyExistException(String.format("Subscriber exists with phone: %s", subscriber.getPhone()));
            }
        });

        subscriber.setSubscriberId(UUID.randomUUID());
        subscriber.setActive(true);
        return Optional.of(subscriberRepository.save(subscriber));
    }
}
