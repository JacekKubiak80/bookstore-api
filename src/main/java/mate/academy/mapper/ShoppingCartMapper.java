package mate.academy.mapper;

import mate.academy.dto.CartItemDto;
import mate.academy.dto.ShoppingCartDto;
import mate.academy.model.CartItem;
import mate.academy.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {

    @Mapping(target = "userId", source = "user.id")
    ShoppingCartDto toDto(ShoppingCart cart);

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem item);
}
