package ru.itis.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.demo.entity.Note;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDto {
    private Long id;
    private String user;
    private String text;

    public static NoteDto from(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .user(note.getUser())
                .text(note.getUser())
                .build();
    }

    public static List<NoteDto> from(List<Note> notes) {
        return notes.stream().map(NoteDto::from).collect(Collectors.toList());
    }
}
