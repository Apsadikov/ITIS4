package ru.itis.demo.repository;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.itis.demo.dto.NoteDto;
import ru.itis.demo.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class NoteRepositoryImpl implements NoteRepository {
    private List<Note> notes;

    public NoteRepositoryImpl() {
        notes = new ArrayList<>();
        for (int i = 0; i < 53; i++) {
            notes.add(new Note(i, "user" + i, String.valueOf(i)));
        }
    }

    @Override
    public List<Note> findAll(int page) {
        return notes
                .subList((page - 1) * RECORD_LIMIT, notes.size() > page * RECORD_LIMIT ? page * RECORD_LIMIT : notes.size());
    }

    @Override
    public int getTotalPage() {
        return (int) Math.ceil((float) notes.size() / RECORD_LIMIT);
    }

    @Override
    public List<Note> findAllByName(int page, String name) {
        List<Note> notes = this.notes.stream().filter(note -> note.getName().contains(name)).collect(Collectors.toList());
        return notes.subList((page - 1) * RECORD_LIMIT, notes.size() > page * RECORD_LIMIT ? page * RECORD_LIMIT : notes.size());
    }

    @Override
    public int getTotalPage(String name) {
        return (int) Math.ceil((float) notes.stream()
                .filter(note -> note.getName().contains(name))
                .collect(Collectors.toList()).size() / RECORD_LIMIT);
    }

    @Override
    public void addNote(NoteDto noteDto) {
        notes.add(0, Note.builder()
                .id(notes.size())
                .name(noteDto.getName())
                .text(noteDto.getText())
                .build());
    }
}
