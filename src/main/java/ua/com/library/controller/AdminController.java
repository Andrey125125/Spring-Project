package ua.com.library.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/admin")
    public String viewBuses(Model model, @RequestParam("page") Optional<Integer> page,
                            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy ) {
        addPagination(model, page, sortBy);
        return "/admin";
    }



//    @GetMapping("/bus/edit/{code}")
//    public String editBus(Model model, @PathVariable Integer code, @RequestParam("page") Optional<Integer> page) {
//        model.addAttribute("editCode", code);
//        addPagination(model, page);
//        return "/bus";
//    }
//
//    @PostMapping("/bus/edit/{code}")
//    public ModelAndView updateBus(Model model, @PathVariable Integer code,
//                                  @RequestParam("changeMileage") Double changeMileage, @RequestParam("changeConsumption") Double changeConsumption,
//                                  @RequestParam("changeDriver") Long idDriver,
//                                  @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
//        bookService.changeBus(code, changeMileage, changeConsumption, idDriver);
//        addPagination(model, page);
//        return new ModelAndView("redirect:/bus");
//    }

    private void addPagination(Model model, Optional<Integer> current, String sortBy) {
        int currentPage = current.orElse(1);
        int pageSize = 2;
        Page<Book> books = bookService.showPageList(currentPage, pageSize, sortBy);
        model.addAttribute("books", books);
        model.addAttribute("currentPage", currentPage);
        int totalPages = books.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
