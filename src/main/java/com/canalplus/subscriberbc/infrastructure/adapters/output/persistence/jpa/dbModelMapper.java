package com.canalplus.subscriberbc.infrastructure.adapters.output.persistence.jpa;

import com.canalplus.subscriberbc.domain.model.Subscriber;
import org.mapstruct.Mapper;

@Mapper
public interface dbModelMapper {
    Subscriber toSubscriber(SubscriberEntity subscriberEntity);
    SubscriberEntity toEntity(Subscriber Subscriber);
}
