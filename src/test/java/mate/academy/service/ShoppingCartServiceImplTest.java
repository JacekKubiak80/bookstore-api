package mate.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import mate.academy.dto.AddCartItemRequestDto;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    private ShoppingCartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ShoppingCartMapper cartMapper;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ShoppingCartServiceImpl cartService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("user@test.com");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getEmail(), null)
        );

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
    }

    @Test
    void getCart_shouldCreateCartIfNotExists() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        when(cartRepository.save(any())).thenReturn(cart);
        when(cartMapper.toDto(cart)).thenReturn(null);

        assertNotNull(cartService.getCart());

        verify(cartRepository).save(any());
    }

    @Test
    void addBook_shouldAddNewItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        Book book = new Book();
        book.setId(10L);

        AddCartItemRequestDto request = new AddCartItemRequestDto();
        request.setBookId(10L);
        request.setQuantity(2);

        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(bookRepository.findById(10L)).thenReturn(Optional.of(book));
        when(cartMapper.toDto(cart)).thenReturn(null);

        cartService.addBook(request);

        assertEquals(1, cart.getCartItems().size());
    }

    @Test
    void updateQuantity_shouldUpdateItem() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        CartItem item = new CartItem();
        item.setId(5L);
        item.setQuantity(1);
        item.setShoppingCart(cart);

        UpdateCartItemRequestDto request = new UpdateCartItemRequestDto();
        request.setQuantity(5);

        when(cartItemRepository.findByIdAndShoppingCartUser(5L, user))
                .thenReturn(Optional.of(item));
        when(cartMapper.toDto(cart)).thenReturn(null);

        cartService.updateQuantity(5L, request);

        assertEquals(5, item.getQuantity());
    }

    @Test
    void deleteItem_shouldDeleteCartItem() {
        CartItem item = new CartItem();
        item.setId(7L);

        when(cartItemRepository.findByIdAndShoppingCartUser(7L, user))
                .thenReturn(Optional.of(item));

        cartService.deleteItem(7L);

        verify(cartItemRepository).delete(item);
    }

    @Test
    void addBook_shouldThrowExceptionWhenBookNotFound() {
        AddCartItemRequestDto request = new AddCartItemRequestDto();
        request.setBookId(99L);
        request.setQuantity(1);

        when(cartRepository.findByUser(user))
                .thenReturn(Optional.of(new ShoppingCart()));
        when(bookRepository.findById(99L))
                .thenReturn(Optional.empty());

        try {
            cartService.addBook(request);
        } catch (EntityNotFoundException e) {
            assertEquals("Book not found", e.getMessage());
        }
    }
}
