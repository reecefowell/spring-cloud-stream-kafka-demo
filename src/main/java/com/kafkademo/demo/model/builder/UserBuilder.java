package com.kafkademo.demo.model.builder;

import com.kafkademo.demo.model.User;
import io.codearte.jfairy.Fairy;

public class UserBuilder {
    private static Fairy fairy = Fairy.create();

    private Long id;
    private String firstName;
    private String lastName;

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public static UserBuilder aUser(User user) {
        return new UserBuilder(user);
    }

    public UserBuilder() {
        withId();
        withFirstName();
        withLastName();
    }

    public UserBuilder(User user) {
        withId(user.getId());
        withFirstName(user.getFirstName());
        withLastName(user.getLastName());
    }

    public User build() {
        return new User()
            .setId(id)
            .setFirstName(firstName)
            .setLastName(lastName);
    }

    public UserBuilder withId() {
        id = fairy.baseProducer().randomBetween(1L, Long.MAX_VALUE);
        return this;
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withFirstName() {
        firstName = fairy.person().firstName();
        return this;
    }

    public UserBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder withLastName() {
        lastName = fairy.person().lastName();
        return this;
    }

    public UserBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
