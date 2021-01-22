package ru.itis.resumeapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.resumeapi.dto.ErrorDto;
import ru.itis.resumeapi.dto.LoginDto;
import ru.itis.resumeapi.dto.RegistrationDto;
import ru.itis.resumeapi.dto.TokenDto;
import ru.itis.resumeapi.services.UserService;

import java.util.Optional;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthorizationController {
    private final UserService userService;

    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody RegistrationDto registrationDto) {
        return userService.signUp(registrationDto) ? ResponseEntity.ok().build() : ResponseEntity.status(401).body(ErrorDto.builder().error("User with this email is already registered").build());
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto) {
        Optional<TokenDto> tokenDtoCandidate = userService.signIn(loginDto);
        return tokenDtoCandidate.isPresent() ? ResponseEntity.ok(tokenDtoCandidate.get()) : ResponseEntity.status(401).body(ErrorDto.builder().error("Invalid authentication data").build());
    }
}
