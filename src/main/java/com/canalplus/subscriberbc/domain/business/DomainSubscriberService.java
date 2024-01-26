package com.canalplus.subscriberbc.domain.business;

import com.canalplus.subscriberbc.application.ports.input.crud.SubscriberOperation;
import com.canalplus.subscriberbc.application.ports.input.search.LookupSrvice;
import com.canalplus.subscriberbc.application.ports.output.SubscriberRepository;
import com.canalplus.subscriberbc.domain.model.Subscriber;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DomainSubscriberService implements SubscriberOperation, LookupSrvice {

    private final SubscriberRepository subscriberRepository;

    public DomainSubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public Optional<Subscriber> create(Subscriber subscriber) {
        CreateSubscriberCommand createCommand = new CreateSubscriberCommand(subscriber, subscriberRepository);
        return new CommandExecutor().executeCommand(createCommand);
    }

    @Override
    public Optional<Subscriber> read(UUID id) {
        ReadSubscriberCommand readCommand = new ReadSubscriberCommand(id, subscriberRepository);
        return new CommandExecutor().executeCommand(readCommand);
    }

    @Override
    public Optional<Subscriber> update(Subscriber subscriber) {
        UpdateSubscriberCommand updateCommand = new UpdateSubscriberCommand(subscriber, subscriberRepository);
        return new CommandExecutor().executeCommand(updateCommand);
    }

    @Override
    public Optional<Subscriber> cancelSubscription(UUID id) {
        CancelSubscriptionCommand cancelCommand = new CancelSubscriptionCommand(id, subscriberRepository);
        return new CommandExecutor().executeCommand(cancelCommand);
    }

    @Override
    public List<Subscriber> searchSubscribers(SubscriberSearchRequest searchRequest) {
        return subscriberRepository.findAll(
                searchRequest.getSearchCriteria(),
                searchRequest.getPageNumber(),
                searchRequest.getPageSize()
        );
    }
}
