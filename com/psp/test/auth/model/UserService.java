package com.psp.test.auth.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface UserService extends org.springframework.security.core.userdetails.UserDetailsService {

    Optional<UserDTO> findOne(Long id);

    Optional<UserDTO> findMe();

    Page<UserDTO> findAll(PageRequest pageable);

    User create(UserParams params);

    User update(User user, UserParams params);

    User updateMe(UserParams params);


}
