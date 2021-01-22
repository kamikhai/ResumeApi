package ru.itis.resumeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.resumeapi.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String surname;
    private String name;
    private String patronymic;
    private String email;

    public static UserDto fromUser(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .patronymic(user.getPatronymic())
                .build();
    }
}
