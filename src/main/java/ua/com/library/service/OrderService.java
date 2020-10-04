package ua.com.library.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.library.entity.Book;
import ua.com.library.entity.Order;
import ua.com.library.entity.User;
import ua.com.library.repository.BookRepository;
import ua.com.library.repository.OrderRepository;
import ua.com.library.repository.UserRepository;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {
    BookRepository bookRepository;
    UserRepository userRepository;
    OrderRepository orderRepository;


    @Transactional
    public void orderBook(long id){
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow( () ->new RuntimeException("your session have expired"));

        Book book = bookRepository.getOne(id);

        Order order = Order.builder()
                .book(book).user(user)
                .status(Order.Status.REQUESTED)
                .returningDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .build();

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        orderRepository.save(order);


    }

    public Page<Order> resolvePagesOrderAdmin(int currentPage, int pageSize) {


        return orderRepository.findAll(PageRequest.of(currentPage - 1, pageSize));


    }
}
