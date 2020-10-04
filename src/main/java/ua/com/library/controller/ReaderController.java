package ua.com.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.library.entity.Book;
import ua.com.library.service.BookService;
import ua.com.library.service.OrderService;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReaderController {

    private final BookService bookService;
    private final OrderService orderService;
    private final static int pageSize = 2;

    @GetMapping("/reader")
    public String viewBooks(Model model,
                            @RequestParam(defaultValue = "1") Integer currentPage,
                            @RequestParam(defaultValue = "name") String sortBy,
                            @RequestParam(defaultValue = "") String searchBy) {

        Page<Book> books = bookService.resolvePagesBookReader(currentPage, pageSize, sortBy, searchBy);
        model.addAttribute("books", books);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("searchBy", searchBy);

        bookService.findPageNumbers(books.getTotalPages())
                .ifPresent(pageNumbers -> model.addAttribute("pageNumbers", pageNumbers));

        return "/reader";
    }

    @GetMapping("/reader/order/{id}")
    public String orderBook(@PathVariable Integer id){
        //todo: finish implementation
        orderService.orderBook(id);
        return "redirect:/reader";
    }
}
