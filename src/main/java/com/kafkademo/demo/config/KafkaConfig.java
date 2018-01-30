package com.kafkademo.demo.config;

import com.kafkademo.demo.datasync.UserUpdate;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({UserUpdate.class})
public class KafkaConfig {
}
