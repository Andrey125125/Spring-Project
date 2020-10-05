package ua.com.library.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.library.entity.Order;
import ua.com.library.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Page<Order> findAllByStatus(Order.Status status, Pageable pageable);

    List<Order> findAllByUserAndStatus(User user, Order.Status status);

}
