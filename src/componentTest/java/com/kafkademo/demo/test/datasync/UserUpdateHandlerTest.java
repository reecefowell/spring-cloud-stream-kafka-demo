package com.kafkademo.demo.test.datasync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkademo.demo.datasync.UserUpdateHandler;
import com.kafkademo.demo.datasync.UserUpdateHandler.UserEvent;
import com.kafkademo.demo.datasync.UserUpdatesPublish;
import com.kafkademo.demo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.kafkademo.demo.datasync.UserUpdateSubscribe.SUBSCRIBE;
import static com.kafkademo.demo.model.builder.UserBuilder.aUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserUpdateHandlerTest {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    @Qualifier(SUBSCRIBE)
    private SubscribableChannel input;
    @Autowired
    private UserUpdatesPublish output;
    @Autowired
    private MessageCollector messageCollector;
    @Autowired
    private UserUpdateHandler userUpdateHandler;

    @Before
    public void setUp() {
        messageCollector.forChannel(output.userUpdatesPublish()).clear();
    }

    @Test
    public void publishNewUser() {
        User user = aUser().build();

        UserEvent userEvent = new UserEvent();
        userEvent.id = user.getId();
        userEvent.firstName = user.getFirstName();
        userEvent.lastName = user.getLastName();

        userUpdateHandler.publish(userEvent);

        Message<UserEvent> receivedForTarget = (Message<UserEvent>) messageCollector
            .forChannel(output.userUpdatesPublish())
            .poll();

        assertThat(receivedForTarget, is(notNullValue()));
        UserEvent actualForTarget = receivedForTarget.getPayload();
        assertThat(actualForTarget.id, is(user.getId()));
        assertThat(actualForTarget.firstName, is(user.getFirstName()));
        assertThat(actualForTarget.lastName, is(user.getLastName()));
    }

    @Test
    public void receiveNewUser() throws JsonProcessingException {
        User user = aUser().build();

        UserEvent userEvent = new UserEvent();
        userEvent.id = user.getId();
        userEvent.firstName = user.getFirstName();
        userEvent.lastName = user.getLastName();

        String payload = mapper.writeValueAsString(userEvent);

        input.send(MessageBuilder.withPayload(payload).build());
    }
}
