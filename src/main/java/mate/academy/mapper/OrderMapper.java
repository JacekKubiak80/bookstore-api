package mate.academy.mapper;

import mate.academy.dto.OrderDto;
import mate.academy.dto.OrderItemDto;
import mate.academy.model.Order;
import mate.academy.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);

    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toItemDto(OrderItem item);
}
