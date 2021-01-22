package ru.itis.resumeapi.services;

import ru.itis.resumeapi.dto.CreateResumeDto;
import ru.itis.resumeapi.models.Resume;
import ru.itis.resumeapi.models.User;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    List<Resume> findAll();

    Optional<Resume> findById(String id, User user);

    Resume save(Resume resume);

    Optional<Resume> update(String id, CreateResumeDto createResumeDto, User user);

    boolean delete(String id, User user);
}
