package ru.itis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.itis.demo.dto.NoteDto;
import ru.itis.demo.model.Note;
import ru.itis.demo.repository.NoteRepository;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void addNote(NoteDto noteDto) {
        noteRepository.addNote(noteDto);
    }

    @Override
    public List<Note> pagination(int page, String name) {
        return noteRepository.findAllByName(1, name);
    }

    @Override
    public List<Note> pagination(int page) {
        return noteRepository.findAll(page);
    }

    @Override
    public int totalPage(String name) {
        return noteRepository.getTotalPage(name);
    }

    @Override
    public int totalPage() {
        return noteRepository.getTotalPage();
    }
}
