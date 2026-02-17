package mate.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import mate.academy.dto.AddCartItemRequestDto;
import mate.academy.dto.CartItemDto;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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

    // Spy pozwala mockować wybrane metody w serwisie
    @Spy
    @InjectMocks
    private ShoppingCartServiceImpl cartService;

    private User user;
    private ShoppingCart cart;
    private Book book;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        // Tworzymy tylko proste obiekty, bez logiki security
        user = new User();
        user.setId(1L);
        user.setEmail("user@test.com");

        cart = new ShoppingCart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setCartItems(new HashSet<>());

        book = new Book();
        book.setId(5L);
        book.setTitle("Test Book");

        cartItem = new CartItem();
        cartItem.setId(10L);
        cartItem.setBook(book);
        cartItem.setShoppingCart(cart);
        cartItem.setQuantity(3);

        // Mockujemy getCurrentUser(), aby testy nie polegały na SecurityContext
        doReturn(user).when(cartService).getCurrentUser();
    }

    @Test
    void addBook_shouldReturnExpectedDto() {
        // given
        AddCartItemRequestDto request = new AddCartItemRequestDto();
        request.setBookId(book.getId());
        request.setQuantity(3);

        cart.setCartItems(Set.of(cartItem));

        ShoppingCartDto expected = new ShoppingCartDto();
        expected.setId(cart.getId());
        expected.setUserId(user.getId());
        expected.setCartItems(Set.of(
                new CartItemDto(cartItem.getId(), user.getId(), book.getId(), cartItem.getQuantity())
        ));

        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(cartMapper.toDto(cart)).thenReturn(expected);

        // when
        ShoppingCartDto actual = cartService.addBook(request);

        // then
        assertEquals(expected, actual);
        verify(cartMapper).toDto(cart);
    }

    @Test
    void updateQuantity_shouldReturnExpectedDto() {
        // given
        UpdateCartItemRequestDto request = new UpdateCartItemRequestDto();
        request.setQuantity(5);

        ShoppingCartDto expected = new ShoppingCartDto();
        expected.setId(cart.getId());
        expected.setUserId(user.getId());
        expected.setCartItems(Set.of(
                new CartItemDto(cartItem.getId(), user.getId(), book.getId(), request.getQuantity())
        ));

        when(cartItemRepository.findByIdAndShoppingCartUser(cartItem.getId(), user))
                .thenReturn(Optional.of(cartItem));
        when(cartMapper.toDto(cart)).thenReturn(expected);

        // when
        ShoppingCartDto actual = cartService.updateQuantity(cartItem.getId(), request);

        // then
        assertEquals(5, cartItem.getQuantity());
        assertEquals(expected, actual);
        verify(cartMapper).toDto(cart);
    }

    @Test
    void deleteItem_shouldDeleteCartItem() {
        // given
        when(cartItemRepository.findByIdAndShoppingCartUser(cartItem.getId(), user))
                .thenReturn(Optional.of(cartItem));

        // when
        cartService.deleteItem(cartItem.getId());

        // then
        verify(cartItemRepository).delete(cartItem);
    }

    @Test
    void addBook_shouldThrowExceptionWhenBookNotFound() {
        // given
        AddCartItemRequestDto request = new AddCartItemRequestDto();
        request.setBookId(99L);
        request.setQuantity(1);

        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        EntityNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> cartService.addBook(request)
        );

        assertEquals("Book not found", exception.getMessage());
    }
}

