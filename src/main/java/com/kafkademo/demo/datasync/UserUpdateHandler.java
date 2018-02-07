package com.kafkademo.demo.datasync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import static com.kafkademo.demo.datasync.UserUpdateSubscribe.SUBSCRIBE;
import static com.kafkademo.demo.datasync.UserUpdatesPublish.PUBLISH;

@Component
public class UserUpdateHandler {
    @Autowired
    @Qualifier(PUBLISH)
    private MessageChannel output;

    public void publish(UserEvent userEvent) {
        output.send(MessageBuilder
            .withPayload(userEvent)
            .build());
    }

    @StreamListener(SUBSCRIBE)
    public void subscribe(UserEvent userEvent) {
        System.out.println(String.format("Receiving user %s", userEvent.id));
    }

    public static class UserEvent {
        public Long id;
        public String firstName;
        public String lastName;
    }
}
