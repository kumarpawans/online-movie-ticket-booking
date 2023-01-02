package com.psp.test.auth.repository;

import com.psp.test.auth.model.User;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserCustomRepository extends Repository<User, Long> {

    Optional<Row> findOne(Long userId);

    @Value
    @Builder
    class Row {
        private final User user;
    }
}
