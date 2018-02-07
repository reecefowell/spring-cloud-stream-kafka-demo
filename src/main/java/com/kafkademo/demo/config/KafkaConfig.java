package com.kafkademo.demo.config;

import com.kafkademo.demo.datasync.UserUpdatesPublish;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({UserUpdatesPublish.class})
public class KafkaConfig {
}
