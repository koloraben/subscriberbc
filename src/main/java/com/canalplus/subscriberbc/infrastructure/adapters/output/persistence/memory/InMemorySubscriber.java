package com.canalplus.subscriberbc.infrastructure.adapters.output.persistence.memory;

import com.canalplus.subscriberbc.application.ports.output.SubscriberRepository;
import com.canalplus.subscriberbc.domain.model.Subscriber;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemorySubscriber implements SubscriberRepository {
    List<Subscriber> subscribers = new ArrayList<>();

    public InMemorySubscriber() {

        subscribers.add(new Subscriber(UUID.randomUUID(), "chris", "elinor", "chris@gmail.com", "+33758914602", true));
        subscribers.add(new Subscriber(UUID.randomUUID(), "sidhar", "avaya", "sidhar@gmail.com", "+33998674602", true));
        subscribers.add(new Subscriber(UUID.randomUUID(), "aya", "binoma", "aya@gmail.com", "+33758674662", true));
        subscribers.add(new Subscriber(UUID.randomUUID(), "sozan", "loic", "sozan@gmail.com", "+33754474602", true));
        subscribers.add(new Subscriber(UUID.randomUUID(), "yann", "lamotte", "yann@gmail.com", "+33758688602", false));

    }

    @Override
    public Optional<Subscriber> findById(UUID id) {
        return subscribers.stream().filter(e -> e.getSubscriberId().equals(id)).findFirst();
    }

    @Override
    public Subscriber save(Subscriber subscriber) {
        subscribers.add(subscriber);
        return subscribers.get(subscribers.indexOf(subscriber));
    }

    @Override
    public List<Subscriber> findAll(SubscriberSearchCriteria searchCriteria, int pageNumber, int pageSize) {
        /*int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, subscribers.size());

        return subscribers.stream()
                .filter(searchCriteria)
                .skip(startIndex)
                .limit(endIndex - startIndex)
                .collect(Collectors.toList());*/
        return subscribers;
    }

    @Override
    public Optional<Subscriber> findByEmailOrPhone(String mail, String phone) {
        return subscribers.stream().filter(subscriber -> subscriber.getPhone().equalsIgnoreCase(phone) || subscriber.getMail().equalsIgnoreCase(mail)).findFirst();
    }

    @Override
    public Boolean update(Subscriber updatedSubscriber) {
        Optional<Subscriber> existingSubscriber = subscribers.stream()
                .filter(subscriber -> subscriber.getSubscriberId().equals(updatedSubscriber.getSubscriberId()))
                .findFirst();

        existingSubscriber.ifPresent(subscriber -> subscribers.set(subscribers.indexOf(subscriber), updatedSubscriber));

        return existingSubscriber.isPresent();
    }
}
