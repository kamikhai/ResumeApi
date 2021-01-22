package ru.itis.resumeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDto {
    private String surname;
    private String name;
    private String patronymic;
    private String email;
    private String password;
}
