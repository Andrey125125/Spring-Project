package ua.com.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.library.entity.Book;
import ua.com.library.service.BookService;


@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final BookService bookService;
    private final static int pageSize = 2;

    @GetMapping("/admin")
    public String viewBuses(Model model,
                            @RequestParam(defaultValue = "1") Integer currentPage,
                            @RequestParam(defaultValue = "name") String sortBy,
                            @RequestParam(defaultValue = "") String searchBy) {

        Page<Book> books = bookService.resolvePages(currentPage, pageSize, sortBy, searchBy);
        model.addAttribute("books", books);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("searchBy", searchBy);

        bookService.findPageNumbers(books.getTotalPages())
                .ifPresent(pageNumbers -> model.addAttribute("pageNumbers", pageNumbers));

        return "/admin";
    }


}
