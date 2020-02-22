package ru.itis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.demo.dto.NoteDto;
import ru.itis.demo.entity.Note;
import ru.itis.demo.repository.NoteRepository;
import ru.itis.demo.service.NoteService;

import static ru.itis.demo.repository.NoteRepository.PAGE_LIMIT;

@Controller
public class NoteController {
    private NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping(value = "/notes/page/{page:[\\d]+}", method = RequestMethod.GET)
    public String getNotes(@PathVariable int page, @RequestParam(name = "name", defaultValue = "") String name, Model model) {
        if (page < 1) {
            return "redirect:/notes/page/1?name=" + name;
        }
        Page<Note> notePage = name.equals("")
                ? noteService.pagination(page - 1) : noteService.pagination(page - 1, name);
        if (notePage.getTotalPages() != 0 && page > notePage.getTotalPages()) {
            return "redirect:/notes/page/" + notePage.getTotalPages() + "?name=" + name;
        }

        if (page > 2) {
            if (notePage.getTotalPages() - page < PAGE_LIMIT - 2) {
                model.addAttribute("lastPage", notePage.getTotalPages());
                model.addAttribute("startPage", notePage.getTotalPages() - PAGE_LIMIT + 1 > 0
                        ? notePage.getTotalPages() - PAGE_LIMIT + 1 : 1);
            } else {
                model.addAttribute("startPage", page - 1);
                model.addAttribute("lastPage", page + PAGE_LIMIT - 2 > notePage.getTotalPages()
                        ? notePage.getTotalPages() : page + PAGE_LIMIT - 2);
            }
        } else {
            model.addAttribute("startPage", 1);
            model.addAttribute("lastPage", notePage.getTotalPages() > PAGE_LIMIT ? PAGE_LIMIT : notePage.getTotalPages());
        }
        model.addAttribute("totalPage", notePage.getTotalPages());
        model.addAttribute("name", name);
        model.addAttribute("page", page);
        model.addAttribute("notes", notePage.toList());
        model.addAttribute("limitPage", NoteRepository.PAGE_LIMIT);
        return "notes";
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public String getNotes(@RequestParam(name = "name", defaultValue = "") String name, Model model) {
        Page<Note> notePage = name.equals("")
                ? noteService.pagination(0) : noteService.pagination(0, name);
        model.addAttribute("notes", notePage.toList());
        model.addAttribute("startPage", 1);
        model.addAttribute("name", name);
        model.addAttribute("page", 1);
        model.addAttribute("totalPage", notePage.getTotalPages());
        model.addAttribute("limitPage", NoteRepository.PAGE_LIMIT);
        model.addAttribute("lastPage", notePage.getTotalPages() > PAGE_LIMIT ? PAGE_LIMIT : notePage.getTotalPages());
        return "notes";
    }

    @GetMapping("/note/add")
    public String addNote() {
        return "note-add";
    }

    @PostMapping("/note/add")
    public String addNewNote(NoteDto noteDto) {
        noteService.addNote(noteDto);
        return "redirect:/notes";
    }
}
