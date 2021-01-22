package ru.itis.resumeapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.itis.resumeapi.dto.CreateResumeDto;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Resume {
    @Id
    private String id;
    @DBRef
    private User user;
    private Date birthDate;
    private String phoneNumber;
    private String education;
    private String workExperience;
    private String skills;

    public static Resume fromDto(CreateResumeDto createResumeDto, User user) {
        return Resume.builder()
                .birthDate(createResumeDto.getBirthDate())
                .phoneNumber(createResumeDto.getPhoneNumber())
                .education(createResumeDto.getEducation())
                .workExperience(createResumeDto.getWorkExperience())
                .skills(createResumeDto.getSkills())
                .user(user)
                .build();
    }
}
