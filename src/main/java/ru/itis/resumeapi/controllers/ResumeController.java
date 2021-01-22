package ru.itis.resumeapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.itis.resumeapi.dto.CreateResumeDto;
import ru.itis.resumeapi.dto.ResumeDto;
import ru.itis.resumeapi.models.Resume;
import ru.itis.resumeapi.models.User;
import ru.itis.resumeapi.security.details.UserDetailsImpl;
import ru.itis.resumeapi.services.ResumeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<ResumeDto> addResume(@RequestBody CreateResumeDto createResumeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        return ResponseEntity.ok(ResumeDto.fromResume(resumeService.save(Resume.fromDto(createResumeDto, user))));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ResumeDto>> findAll() {
        return ResponseEntity.ok(resumeService.findAll().stream().map(ResumeDto::fromResume).collect(Collectors.toList()));
    }

    // далее совершается проверка, является ли пользователь администратором или владельцем резюме
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<Resume> resumeCandidate = resumeService.findById(id, user);
        return resumeCandidate.isPresent() ? ResponseEntity.ok(ResumeDto.fromResume(resumeCandidate.get())) : ResponseEntity.status(404).body("There is no resume with the given id");
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateResume(@PathVariable("id") String id, @RequestBody CreateResumeDto createResumeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        Optional<Resume> resumeCandidate = resumeService.update(id, createResumeDto, user);
        return resumeCandidate.isPresent() ? ResponseEntity.ok(ResumeDto.fromResume(resumeCandidate.get())) : ResponseEntity.status(404).body("Incorrect data entered");
    }

    // далее совершается проверка, является ли пользователь администратором или владельцем резюме
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResume(@PathVariable("id") String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        return resumeService.delete(id, user) ? ResponseEntity.ok().build() : ResponseEntity.status(404).body("There is no resume with the given id");
    }


}
