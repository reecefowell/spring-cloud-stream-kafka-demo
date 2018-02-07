package com.kafkademo.demo.datasync;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserUpdatesPublish {
    String PUBLISH = "user-updates-publish";

    @Output(PUBLISH)
    MessageChannel userUpdatesPublish();
}
