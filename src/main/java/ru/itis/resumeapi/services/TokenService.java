package ru.itis.resumeapi.services;


import ru.itis.resumeapi.models.User;

public interface TokenService {
    String getToken(User user);
}
