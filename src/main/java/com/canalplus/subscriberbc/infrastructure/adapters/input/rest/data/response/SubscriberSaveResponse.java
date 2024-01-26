package com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubscriberSaveResponse extends RepresentationModel<SubscriberSaveResponse> {

    private final UUID subscriberId;
    private final String firstName;
    private final String lastName;
    private final String mail;
    private final String phone;
    private final boolean isActive;

    public SubscriberSaveResponse(UUID subscriberId, String firstName, String lastName, String mail, String phone, boolean isActive) {
        this.subscriberId = subscriberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phone = phone;
        this.isActive = isActive;
    }

}
