package com.kafkademo.demo.datasync;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface UserUpdateSubscribe {
    String SUBSCRIBE = "user-updates-subscribe";

    @Input(SUBSCRIBE)
    SubscribableChannel userUpdatesSubscribe();
}
