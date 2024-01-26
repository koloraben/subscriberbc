package com.canalplus.subscriberbc.application.ports.input.search;

import com.canalplus.subscriberbc.domain.model.Subscriber;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSearchRequest;

import java.util.List;

public interface LookupSrvice {
    List<Subscriber> searchSubscribers(SubscriberSearchRequest searchRequest);
}
