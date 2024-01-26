package com.canalplus.subscriberbc.application.ports.output;

import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchCriteria;
import com.canalplus.subscriberbc.domain.model.Subscriber;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriberRepository {
    Optional<Subscriber> findById(UUID id);

    Subscriber save(Subscriber subscriber);

    List<Subscriber> findAll(SubscriberSearchCriteria searchCriteria, int pageNumber, int pageSize);

    Optional<Subscriber>  findByEmailOrPhone(String mail, String phone);

    Boolean update(Subscriber updatedSubscriber);
}
