package ru.itis.resumeapi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.resumeapi.models.Resume;

public interface ResumeRepository extends MongoRepository<Resume, String> {
}
