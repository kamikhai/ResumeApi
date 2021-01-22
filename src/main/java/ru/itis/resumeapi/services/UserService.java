package ru.itis.resumeapi.services;

import ru.itis.resumeapi.dto.RegistrationDto;
import ru.itis.resumeapi.dto.TokenDto;
import ru.itis.resumeapi.dto.LoginDto;
import ru.itis.resumeapi.models.User;

import java.util.Optional;

public interface UserService {

    boolean signUp(RegistrationDto registrationDto);

    Optional<TokenDto> signIn(LoginDto loginDto);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);
}
