package com.canalplus.subscriberbc.infrastructure.adapters.output.persistence.jpa;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "subscriber")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriberEntity {
    @Id
    @Column(name = "id")
    private UUID subscriberId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mail")
    private String mail;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_active")
    private boolean isActive;

    public boolean getIsActive() {
        return isActive;
    }
}
