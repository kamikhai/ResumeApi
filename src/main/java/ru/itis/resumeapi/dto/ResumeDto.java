package ru.itis.resumeapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.resumeapi.models.Resume;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeDto {
    private String id;
    private UserDto user;
    private Date birthDate;
    private String phoneNumber;
    private String education;
    private String workExperience;
    private String skills;

    public static ResumeDto fromResume(Resume resume){
        return ResumeDto.builder()
                .id(resume.getId())
                .birthDate(resume.getBirthDate())
                .education(resume.getEducation())
                .phoneNumber(resume.getPhoneNumber())
                .skills(resume.getSkills())
                .user(UserDto.fromUser(resume.getUser()))
                .workExperience(resume.getWorkExperience())
                .build();
    }
}
