package com.sbpa.demo.controller;

import com.sbpa.demo.entity.Book;
import com.sbpa.demo.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class BookController {
    private final BookRepo repo;

    @GetMapping("/{reader}")
    public String readersBooks(@PathVariable("reader") String reader, Model model) {
        List<Book> books = repo.findByReader(reader);
        if (books != null) {
            model.addAttribute("books", books);
        }
        return "readingList";
    }

    @PostMapping("/{reader}")
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        repo.save(book);
        return "redirect:/{reader}";
    }
}
