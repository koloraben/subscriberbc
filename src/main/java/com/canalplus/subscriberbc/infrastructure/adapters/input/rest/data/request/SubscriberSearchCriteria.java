package com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriberSearchCriteria {
    private String firstName;
    private String lastName;
    private String mail;
    private String phone;
    private boolean isActive;

    public void setIsActive(boolean b) {
        isActive = b;
    }

}
