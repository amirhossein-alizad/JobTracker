package com.jobhunt.jobtracker.repository;

import com.jobhunt.jobtracker.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
    List<Note> findByApplicationIdOrderByCreatedAtDesc(Long applicationId);
}
