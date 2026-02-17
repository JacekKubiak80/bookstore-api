package mate.academy.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.AddCartItemRequestDto;
import mate.academy.dto.ShoppingCartDto;
import mate.academy.dto.UpdateCartItemRequestDto;
import mate.academy.mapper.ShoppingCartMapper;
import mate.academy.model.Book;
import mate.academy.model.CartItem;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.repository.BookRepository;
import mate.academy.repository.CartItemRepository;
import mate.academy.repository.ShoppingCartRepository;
import mate.academy.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper cartMapper;
    private final UserRepository userRepository;

    User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private ShoppingCart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCartDto getCart() {
        ShoppingCart cart = getOrCreateCart(getCurrentUser());
        return cartMapper.toDto(cart);
    }

    @Override
    public ShoppingCartDto addBook(AddCartItemRequestDto request) {
        ShoppingCart cart = getOrCreateCart(getCurrentUser());
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getBook().getId().equals(book.getId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setShoppingCart(cart);
                    newItem.setBook(book);
                    cart.getCartItems().add(newItem);
                    return newItem;
                });
        item.setQuantity(item.getQuantity() + request.getQuantity());
        return cartMapper.toDto(cart);
    }

    @Override
    public ShoppingCartDto updateQuantity(Long cartItemId, UpdateCartItemRequestDto request) {
        User user = getCurrentUser();

        CartItem item = cartItemRepository
                .findByIdAndShoppingCartUser(cartItemId, user)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart item not found"));
        item.setQuantity(request.getQuantity());
        return cartMapper.toDto(item.getShoppingCart());
    }

    @Override
    public void deleteItem(Long cartItemId) {
        User user = getCurrentUser();
        CartItem item = cartItemRepository
                .findByIdAndShoppingCartUser(cartItemId, user)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cart item not found"));
        cartItemRepository.delete(item);
    }
}
