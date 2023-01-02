package com.psp.test.auth.model;


import java.util.Optional;

public interface SecurityContextService {
    Optional<User> currentUser();
}
