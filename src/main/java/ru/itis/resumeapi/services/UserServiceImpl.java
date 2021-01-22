package ru.itis.resumeapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.resumeapi.dto.LoginDto;
import ru.itis.resumeapi.dto.RegistrationDto;
import ru.itis.resumeapi.dto.TokenDto;
import ru.itis.resumeapi.models.Role;
import ru.itis.resumeapi.models.User;
import ru.itis.resumeapi.repositories.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public boolean signUp(RegistrationDto registrationDto) {
        User user = User.builder()
                .surname(registrationDto.getSurname())
                .name(registrationDto.getName())
                .patronymic(registrationDto.getPatronymic())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role(Role.USER)
                .build();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<TokenDto> signIn(LoginDto loginDto) {
        Optional<User> userCandidate = findByEmail(loginDto.getEmail());
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                return Optional.ofNullable(new TokenDto(tokenService.getToken(user)));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}
