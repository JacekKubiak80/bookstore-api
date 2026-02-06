package mate.academy.service;

import java.util.List;
import mate.academy.dto.CreateOrderRequestDto;
import mate.academy.dto.OrderDto;
import mate.academy.dto.OrderItemDto;
import mate.academy.dto.UpdateOrderStatusRequestDto;

public interface OrderService {
    OrderDto placeOrder(CreateOrderRequestDto request);

    List<OrderDto> getUserOrders();

    List<OrderItemDto> getOrderItems(Long orderId);

    OrderItemDto getOrderItem(Long orderId, Long itemId);

    OrderDto updateStatus(Long id, UpdateOrderStatusRequestDto request);
}
