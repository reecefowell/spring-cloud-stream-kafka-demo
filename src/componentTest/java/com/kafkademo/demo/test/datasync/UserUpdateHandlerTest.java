package com.kafkademo.demo.test.datasync;

import com.kafkademo.demo.datasync.UserUpdateHandler;
import com.kafkademo.demo.datasync.UserUpdateHandler.UserEvent;
import com.kafkademo.demo.datasync.UserUpdatesPublish;
import com.kafkademo.demo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.kafkademo.demo.model.builder.UserBuilder.aUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserUpdateHandlerTest {
    @Autowired
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
    public void sendNewUser() {
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
}
