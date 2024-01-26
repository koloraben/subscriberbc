package com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberSearchRequest {
    private SubscriberSearchCriteria searchCriteria;
    private int pageNumber;
    private int pageSize;
}
