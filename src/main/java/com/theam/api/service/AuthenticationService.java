package com.theam.api.service;

import com.theam.api.model.User;

public interface AuthenticationService {
    User getLoggedUser();
}
