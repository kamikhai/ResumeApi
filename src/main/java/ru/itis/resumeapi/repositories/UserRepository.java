package ru.itis.resumeapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.resumeapi.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
