package com.kafkademo.demo.datasync;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserUpdate {
    String SEND = "user-updates";

    @Output(SEND)
    MessageChannel create();
}
