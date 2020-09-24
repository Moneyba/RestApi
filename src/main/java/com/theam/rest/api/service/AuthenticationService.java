package com.theam.rest.api.service;

import com.theam.rest.api.model.User;

public interface AuthenticationService {
    User getLoggedUser();
}
