package com.kafkademo.demo.datasync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static com.kafkademo.demo.datasync.UserUpdate.SEND;

@Component
public class UserUpdateHandler {
    @Autowired
    @Qualifier(SEND)
    private MessageChannel output;

    public void publish(UserEvent userEvent) {
        output.send(MessageBuilder
            .withPayload(userEvent)
            .build());
    }

    public static class UserEvent {
        public Long id;
        public String firstName;
        public String lastName;
    }
}
