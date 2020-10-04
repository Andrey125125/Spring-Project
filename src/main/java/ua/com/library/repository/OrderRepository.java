package ua.com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.library.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
