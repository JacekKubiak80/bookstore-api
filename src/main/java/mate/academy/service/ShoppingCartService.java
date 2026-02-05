package mate.academy.service;

import mate.academy.dto.AddCartItemRequestDto;
import mate.academy.dto.ShoppingCartDto;
import mate.academy.dto.UpdateCartItemRequestDto;

public interface ShoppingCartService {

    ShoppingCartDto getCart();

    ShoppingCartDto addBook(AddCartItemRequestDto request);

    ShoppingCartDto updateQuantity(Long cartItemId, UpdateCartItemRequestDto request);

    void deleteItem(Long cartItemId);
}
