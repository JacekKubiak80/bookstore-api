package mate.academy.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.AddCartItemRequestDto;
import mate.academy.dto.ShoppingCartDto;
import mate.academy.dto.UpdateCartItemRequestDto;
import mate.academy.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class ShoppingCartController {

    private final ShoppingCartService cartService;

    @GetMapping
    public ShoppingCartDto getCart() {
        return cartService.getCart();
    }

    @PostMapping
    public ShoppingCartDto addBook(
            @Valid @RequestBody AddCartItemRequestDto request) {
        return cartService.addBook(request);
    }

    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartDto updateQuantity(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequestDto request) {
        return cartService.updateQuantity(cartItemId, request);
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long cartItemId) {
        cartService.deleteItem(cartItemId);
    }
}
