package ru.itis.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.demo.dto.NoteDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class NoteController {
    private static final int RECORD_LIMIT = 6;
    private static final int PAGE_LIMIT = 3;
    private List<NoteDto> notes;

    public NoteController() {
        notes = new ArrayList<>();
        for (int i = 0; i < 53; i++) {
            notes.add(new NoteDto("user" + i, String.valueOf(i)));
        }
    }

    @RequestMapping(value = "/notes/page/{page:[\\d]+}", method = RequestMethod.GET)
    public String getNotes(@PathVariable int page, @RequestParam(name = "name", defaultValue = "") String name, Model model) {
        List<NoteDto> notes = name.equals("") ? this.notes :
                this.notes.stream().filter(note -> note.getName().contains(name)).collect(Collectors.toList());

        int totalPage = (int) Math.ceil((float) notes.size() / RECORD_LIMIT);
        if (page > 2) {
            if (totalPage - page < PAGE_LIMIT - 2) {
                model.addAttribute("lastPage", totalPage);
                model.addAttribute("startPage", totalPage - PAGE_LIMIT + 1 > 0 ? totalPage - PAGE_LIMIT + 1 : 1);
            } else {
                model.addAttribute("startPage", page - 1);
                model.addAttribute("lastPage", page + PAGE_LIMIT - 2 > totalPage ? totalPage : page + PAGE_LIMIT - 2);
            }
        } else {
            model.addAttribute("startPage", 1);
            model.addAttribute("lastPage", totalPage > PAGE_LIMIT ? PAGE_LIMIT : totalPage);
        }
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("name", name);
        model.addAttribute("page", page);

        if ((page - 1) * RECORD_LIMIT > notes.size()) {
            notes = new ArrayList<>();
        } else {
            notes = notes.subList((page - 1) * RECORD_LIMIT,
                    notes.size() >= page * RECORD_LIMIT ? page * RECORD_LIMIT : notes.size());
        }
        model.addAttribute("notes", notes);
        return "notes";
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public String getNotes(@RequestParam(name = "name", defaultValue = "") String name, Model model) {
        List<NoteDto> notes = name.equals("") ? this.notes :
                this.notes.stream().filter(note -> note.getName().contains(name))
                        .collect(Collectors.toList());
        int totalPage = (int) Math.ceil((float) notes.size() / RECORD_LIMIT);
        model.addAttribute("notes", notes.subList(0, RECORD_LIMIT));
        model.addAttribute("startPage", 1);
        model.addAttribute("name", name);
        model.addAttribute("page", 1);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("lastPage", totalPage > PAGE_LIMIT ? PAGE_LIMIT : totalPage);
        return "notes";
    }

    @GetMapping("/note/add")
    public String addNote() {
        return "note-add";
    }

    @PostMapping("/note/add")
    public String addNewNote(NoteDto noteDto) {
        notes.add(0, noteDto);
        return "redirect:/notes";
    }
}
