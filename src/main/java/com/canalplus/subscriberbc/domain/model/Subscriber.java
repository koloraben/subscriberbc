package com.canalplus.subscriberbc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscriber {
    private UUID subscriberId;
    private String firstName;
    private String lastName;
    private String mail;
    private String phone;
    private boolean isActive;

    public Subscriber(String firstName, String lastName, String mail, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phone = phone;
        this.isActive = true;
    }


    public boolean getIsActive() {
        return isActive;
    }

}
