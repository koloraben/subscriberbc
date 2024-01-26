package com.canalplus.subscriberbc.infrastructure.adapters.input.rest.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SubscriberSaveRequest(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @Email
        @NotNull
        String mail,
        @NotNull
        @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$")
        String phone
) {}
