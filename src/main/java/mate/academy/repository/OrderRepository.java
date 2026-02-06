package mate.academy.repository;

import java.util.List;
import mate.academy.model.Order;
import mate.academy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
