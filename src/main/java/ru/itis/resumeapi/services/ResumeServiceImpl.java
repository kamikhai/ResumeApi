package ru.itis.resumeapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.itis.resumeapi.dto.CreateResumeDto;
import ru.itis.resumeapi.models.Resume;
import ru.itis.resumeapi.models.Role;
import ru.itis.resumeapi.models.User;
import ru.itis.resumeapi.repositories.ResumeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

    @Override
    public List<Resume> findAll() {
        return resumeRepository.findAll();
    }

    @Override
    public Optional<Resume> findById(String id, User user) {
        Optional<Resume> resumeCandidate = resumeRepository.findById(id);
        if (checkToAdminOrOwner(resumeCandidate, user)) {
            return resumeCandidate;
        }
        return Optional.empty();
    }

    private boolean checkToAdminOrOwner(Optional<Resume> resumeCandidate, User user) {
        if (resumeCandidate.isPresent()) {
            if (user.getRole().equals(Role.ADMIN) || resumeCandidate.get().getUser().getId().equals(user.getId())) {
                return true;
            } else throw new AccessDeniedException("Permission denied");
        }
        return false;
    }

    @Override
    public boolean delete(String id, User user) {
        Optional<Resume> resumeCandidate = findById(id, user);
        if (checkToAdminOrOwner(resumeCandidate, user)) {
            resumeRepository.delete(resumeCandidate.get());
            return true;
        }
        return false;
    }

    @Override
    public Resume save(Resume resume) {
        return resumeRepository.save(resume);
    }

    @Override
    public Optional<Resume> update(String id, CreateResumeDto createResumeDto, User user) {
        Optional<Resume> resumeCandidate = findById(id, user);
        if (resumeCandidate.isPresent()) {
            Resume resume = resumeCandidate.get();
            if (user.getId().equals(resume.getUser().getId())) {
                return Optional.of(updateData(createResumeDto, resume));
            } else throw new AccessDeniedException("You do not have access to this resume");
        } else return Optional.empty();
    }

    private Resume updateData(CreateResumeDto createResumeDto, Resume resume) {
        resume.setBirthDate(createResumeDto.getBirthDate());
        resume.setEducation(createResumeDto.getEducation());
        resume.setPhoneNumber(createResumeDto.getPhoneNumber());
        resume.setSkills(createResumeDto.getSkills());
        resume.setWorkExperience(resume.getWorkExperience());
        return resume;
    }
}
