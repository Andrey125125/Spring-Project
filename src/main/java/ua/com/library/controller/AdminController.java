package ua.com.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.library.entity.Book;
import ua.com.library.service.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final BookService bookService;
    private final static int pageSize = 2;

    @GetMapping("/admin")
    public String viewBuses(Model model,
                            @RequestParam(defaultValue = "1") Integer currentPage,
                            @RequestParam(defaultValue = "name") String sortBy,
                            @RequestParam Optional<String> searchValue) {

        Page<Book> books = bookService.resolvePages(currentPage, pageSize, sortBy);
        model.addAttribute("books", books);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("searchValue", searchValue.orElse(""));
//        int totalPages = books.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream
//                    .rangeClosed(1, totalPages)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }

        bookService.findPageNumbers(books.getTotalPages())
                .ifPresent(pageNumbers -> model.addAttribute("pageNumbers", pageNumbers));
        return "/admin";
    }


}
