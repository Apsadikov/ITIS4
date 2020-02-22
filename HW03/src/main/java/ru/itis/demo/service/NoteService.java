package ru.itis.demo.service;

import org.springframework.data.domain.Page;
import ru.itis.demo.dto.NoteDto;
import ru.itis.demo.entity.Note;

public interface NoteService {
    void addNote(NoteDto noteDto);

    Page<Note> pagination(int page, String name);

    Page<Note> pagination(int page);
}
