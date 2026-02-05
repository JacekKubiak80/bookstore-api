package mate.academy.repository;

import java.util.Optional;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);
}
