package ru.itis.demo.service;

import ru.itis.demo.dto.NoteDto;
import ru.itis.demo.model.Note;

import java.util.List;

public interface NoteService {
    void addNote(NoteDto noteDto);

    List<Note> pagination(int page, String name);

    List<Note> pagination(int page);

    int totalPage(String name);

    int totalPage();
}
