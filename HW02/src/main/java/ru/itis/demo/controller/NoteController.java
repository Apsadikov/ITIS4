package ru.itis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itis.demo.dto.NoteDto;
import ru.itis.demo.model.Note;
import ru.itis.demo.service.NoteService;

import java.util.List;

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
        int totalPage = name.equals("") ? noteService.totalPage() : noteService.totalPage(name);
        if (totalPage < page) {
            return "redirect:/notes/page/" + totalPage + "?name=" + name;
        }
        List<Note> notes = name.equals("") ? noteService.pagination(page) : noteService.pagination(page, name);
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
        model.addAttribute("notes", notes);
        return "notes";
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public String getNotes(@RequestParam(name = "name", defaultValue = "") String name, Model model) {
        List<Note> notes = name.equals("") ? noteService.pagination(1) : noteService.pagination(1, name);
        int totalPage = name.equals("") ? noteService.totalPage() : noteService.totalPage(name);
        model.addAttribute("notes", notes);
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
        noteService.addNote(noteDto);
        return "redirect:/notes";
    }
}
