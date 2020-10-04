package ua.com.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.library.entity.Book;
import ua.com.library.entity.Order;
import ua.com.library.service.BookService;
import ua.com.library.service.OrderService;


@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final BookService bookService;
    private final OrderService orderService;
    private final static int pageSize = 2;

    @GetMapping("/admin")
    public String viewBooks(Model model,
                            @RequestParam(defaultValue = "1") Integer currentPage,
                            @RequestParam(defaultValue = "name") String sortBy,
                            @RequestParam(defaultValue = "") String searchBy) {

        Page<Book> books = bookService.resolvePagesBookAdmin(currentPage, pageSize, sortBy, searchBy);
        model.addAttribute("books", books);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("searchBy", searchBy);

        bookService.findPageNumbers(books.getTotalPages())
                .ifPresent(pageNumbers -> model.addAttribute("pageNumbers", pageNumbers));

        return "/admin";
    }

    @GetMapping("/admin/edit")
    public String editBook(Model model,
                           @RequestParam Integer currentPage,
                           @RequestParam String sortByBtn,
                           @RequestParam String searchByBtn,
                           @RequestParam Long id,
                           @RequestParam Integer newQuantity){

        bookService.changeQuantityById(id, newQuantity);
        return viewBooks(model, currentPage, sortByBtn, searchByBtn);

    }

    @GetMapping("/admin/addbook")
    public String eddBook(Model model,
                          @RequestParam String authorForm,
                          @RequestParam String bookNameForm,
                          @RequestParam String publisherForm,
                          @RequestParam Integer amountForm) {
        bookService.addBook(authorForm, bookNameForm, publisherForm, amountForm);
        return "redirect:/admin";

    }

    @GetMapping("/requests")
    public String viewOrders(Model model,
                             @RequestParam(defaultValue = "1") Integer currentPage) {

        Page<Order> orders = orderService.resolvePagesOrderAdmin(currentPage, pageSize);
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", currentPage);


        bookService.findPageNumbers(orders.getTotalPages())
                .ifPresent(pageNumbers -> model.addAttribute("pageNumbers", pageNumbers));

        return "/requests";
    }




}
