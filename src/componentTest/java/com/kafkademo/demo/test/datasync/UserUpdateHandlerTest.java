package com.kafkademo.demo.test.datasync;

import com.kafkademo.demo.datasync.UserUpdate;
import com.kafkademo.demo.datasync.UserUpdateHandler;
import com.kafkademo.demo.datasync.UserUpdateHandler.UserEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserUpdateHandlerTest {
    @Autowired
    private UserUpdate output;
    @Autowired
    private MessageCollector messageCollector;
    @Autowired
    private UserUpdateHandler userUpdateHandler;

    @Before
    public void setUp() {
        messageCollector.forChannel(output.create()).clear();
    }

    @Test
    public void sendNewUser() {
        UserEvent userEvent = new UserEvent();
        userEvent.id = 1L;
        userEvent.firstName = "Reece";
        userEvent.lastName = "Fowell";

        userUpdateHandler.publish(userEvent);

        Message<UserEvent> receivedForTarget = (Message<UserEvent>) messageCollector
            .forChannel(output.create())
            .poll();

        assertThat(receivedForTarget, is(notNullValue()));
        UserEvent actualForTarget = receivedForTarget.getPayload();
        assertThat(actualForTarget.id, is(1L));
        assertThat(actualForTarget.firstName, is("Reece"));
        assertThat(actualForTarget.lastName, is("Fowell"));
    }
}
