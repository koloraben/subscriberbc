package com.canalplus.subscriberbc.domain.business;

import com.canalplus.subscriberbc.domain.model.Subscriber;

import java.util.Optional;

public class CommandExecutor {
    public Optional<Subscriber> executeCommand(SubscriberCommand command) {
        return command.execute();
    }
}
