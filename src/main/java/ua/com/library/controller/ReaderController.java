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
import ua.com.library.entity.Order;
import ua.com.library.service.BookService;
import ua.com.library.service.OrderService;

import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReaderController {

    private final BookService bookService;
    private final OrderService orderService;
    private final static int pageSize = 4;

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

        bookService.findPageNumbers(books.getTotalPages())  //no database requests here
                .ifPresent(pageNumbers -> model.addAttribute("pageNumbers", pageNumbers));

        return "/reader";
    }


    @GetMapping("/cabinet")
    public String viewInfo(Model model) {

        List<Order> orders = orderService.resolveBooksToReturn();

        model.addAttribute("orders", orders);



        return "/cabinet";
    }

    @GetMapping("/reader/order/{id}")
    public String orderBook(@PathVariable Integer id){
        orderService.orderBook(id);
        return "redirect:/reader";
    }

    @GetMapping("/reader/return/{id}")
    public String returnBook(@PathVariable Integer id){

        orderService.returnBook(id);
        return "redirect:/cabinet";
    }
}
