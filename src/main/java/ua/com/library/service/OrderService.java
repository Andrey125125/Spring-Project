package ua.com.library.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {
    BookRepository bookRepository;
    UserRepository userRepository;
    OrderRepository orderRepository;



    public void orderBook(long id){
        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow( () -> new RuntimeException("your session have expired"));

        Book book = decreaseQuantityAndReturnBook(id);

        Order order = Order.builder()
                .book(book).user(user)
                .status(Order.Status.REQUESTED)
                .returningDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .build();

        orderRepository.save(order);


    }

    @Transactional
    public Book decreaseQuantityAndReturnBook(long id){
        Book book = bookRepository.getOne(id);
        int quantity = book.getQuantity() - 1;
        if (quantity < 0) throw new RuntimeException("no enought books");
        book.setQuantity(quantity);
        bookRepository.save(book);
        return book;
    }




    public void returnBook(long id){

        Order order = orderRepository.getOne(id);

        long bookId = order.getBook().getId();
        bookRepository.incrementQuantityById(bookId);

        orderRepository.delete(order);


    }

    public void acceptBook(long id){
//        Order order = orderRepository.getOne(id);
//        order.setStatus(Order.Status.ADMITTED);
//        orderRepository.save(order);
        orderRepository.changeStatusById(Order.Status.ADMITTED, id);
    }

    public Page<Order> resolvePagesOrderAdmin(int currentPage, int pageSize) {


        return orderRepository.findAllByStatus(Order.Status.REQUESTED,  PageRequest.of(currentPage - 1, pageSize));


    }

    public List<Order> resolveBooksToReturn(){

        User user = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow( () -> new RuntimeException("your session have expired"));

        return orderRepository.findAllByUserAndStatus(user, Order.Status.ADMITTED);



    }
}
