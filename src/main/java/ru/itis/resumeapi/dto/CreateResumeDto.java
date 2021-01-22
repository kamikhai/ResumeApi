package ru.itis.resumeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateResumeDto {
    private String id;
    private Date birthDate;
    private String phoneNumber;
    private String education;
    private String workExperience;
    private String skills;
}
