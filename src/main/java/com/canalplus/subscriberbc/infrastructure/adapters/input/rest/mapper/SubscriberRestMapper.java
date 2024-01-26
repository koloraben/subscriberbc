package com.canalplus.subscriberbc.infrastructure.adapters.input.rest.mapper;

import com.canalplus.subscriberbc.domain.model.Subscriber;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request.SubscriberSaveRequest;
import com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.response.SubscriberSaveResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SubscriberRestMapper {
    Subscriber toSubscriber(SubscriberSaveRequest subscriberSaveRequest);

    SubscriberSaveResponse toSubscriberResponse(Subscriber subscriber);

    List<SubscriberSaveResponse> toListSubscriberResponse(List<Subscriber> subscriberList);

}
