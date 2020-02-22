package ru.itis.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.itis.demo.dto.NoteDto;
import ru.itis.demo.entity.Note;
import ru.itis.demo.repository.NoteRepository;

@Component
public class NoteServiceImpl implements NoteService {
    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public void addNote(NoteDto noteDto) {
        Note note = noteRepository.save(Note.builder()
                .text(noteDto.getText())
                .user(noteDto.getUser())
                .build());
        noteDto.setId(note.getId());
    }

    @Override
    public Page<Note> pagination(int page, String name) {
        Pageable pageable = PageRequest.of(page, NoteRepository.RECORD_LIMIT, Sort.by("id").descending());
        return noteRepository.findAllByUserContaining(name, pageable);
    }

    @Override
    public Page<Note> pagination(int page) {
        Pageable pageable = PageRequest.of(page, NoteRepository.RECORD_LIMIT, Sort.by("id").descending());
        return noteRepository.findAll(pageable);
    }
}
