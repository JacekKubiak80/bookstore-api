package mate.academy.repository;

import java.util.Optional;
import mate.academy.model.CartItem;
import mate.academy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByIdAndShoppingCartUser(Long id, User user);
}
