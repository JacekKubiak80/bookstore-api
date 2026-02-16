package mate.academy.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import mate.academy.dto.AddCartItemRequestDto;
import mate.academy.dto.ShoppingCartDto;
import mate.academy.dto.UpdateCartItemRequestDto;
import mate.academy.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ShoppingCartControllerTest {

    private ShoppingCartController controller;
    private ShoppingCartService cartService;

    @BeforeEach
    void setup() {
        cartService = Mockito.mock(ShoppingCartService.class);

        controller = new ShoppingCartController(cartService);

        Mockito.when(cartService.getCart()).thenReturn(new ShoppingCartDto());
        Mockito.when(cartService.addBook(Mockito.any())).thenReturn(new ShoppingCartDto());
        Mockito.when(cartService.updateQuantity(Mockito.anyLong(), Mockito.any()))
                .thenReturn(new ShoppingCartDto());
    }

    @Test
    void getCart_returnsDto() {
        ShoppingCartDto cart = controller.getCart();
        assertNotNull(cart);
    }

    @Test
    void addBook_returnsDto() {
        AddCartItemRequestDto request = new AddCartItemRequestDto();
        request.setBookId(1L);
        request.setQuantity(1);
        ShoppingCartDto result = controller.addBook(request);
        assertNotNull(result);
    }

    @Test
    void updateQuantity_returnsDto() {
        UpdateCartItemRequestDto request = new UpdateCartItemRequestDto();
        request.setQuantity(2);
        ShoppingCartDto result = controller.updateQuantity(1L, request);
        assertNotNull(result);
    }

    @Test
    void deleteItem_callsService() {
        controller.deleteItem(1L);
        Mockito.verify(cartService).deleteItem(1L);
    }

    @Test
    void addBook_missingBookId_returnsDto() {
        AddCartItemRequestDto request = new AddCartItemRequestDto();
        request.setQuantity(1);
        ShoppingCartDto result = controller.addBook(request);
        assertNotNull(result);
    }
}
