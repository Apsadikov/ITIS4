package ru.itis.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.demo.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
    int RECORD_LIMIT = 3;
    int PAGE_LIMIT = 3;

    Page<Note> findAllByUserContaining(String user, Pageable pageable);
}
