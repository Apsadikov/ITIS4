package ru.itis.demo.repository;

import ru.itis.demo.dto.NoteDto;
import ru.itis.demo.model.Note;

import java.util.List;

public interface NoteRepository extends Repository<Note, Integer> {
    int RECORD_LIMIT = 6;
    int PAGE_LIMIT = 3;

    List<Note> findAll(int page);
    int getTotalPage();

    List<Note> findAllByName(int page, String name);
    int getTotalPage(String name);

    void addNote(NoteDto noteDto);
}
