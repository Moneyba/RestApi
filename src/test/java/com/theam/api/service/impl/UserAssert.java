package com.theam.api.service.impl;

import com.theam.api.model.User;
import org.assertj.core.api.AbstractAssert;

public class UserAssert extends AbstractAssert<UserAssert, User> {

    UserAssert(User user) {
        super(user, UserAssert.class);
    }

    static UserAssert assertThat(User actual) {
        return new UserAssert(actual);
    }

    UserAssert hasUsername() {
        isNotNull();
        if (actual.getUsername() == null) {
            failWithMessage(
                    "Expected user to have an username, but it was null"
            );
        }
        return this;
    }
}