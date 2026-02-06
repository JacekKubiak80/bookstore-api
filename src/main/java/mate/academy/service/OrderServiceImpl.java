package mate.academy.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.CreateOrderRequestDto;
import mate.academy.dto.OrderDto;
import mate.academy.dto.OrderItemDto;
import mate.academy.dto.UpdateOrderStatusRequestDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.OrderMapper;
import mate.academy.model.CartItem;
import mate.academy.model.Order;
import mate.academy.model.OrderItem;
import mate.academy.model.OrderStatus;
import mate.academy.model.ShoppingCart;
import mate.academy.model.User;
import mate.academy.repository.OrderItemRepository;
import mate.academy.repository.OrderRepository;
import mate.academy.repository.ShoppingCartRepository;
import mate.academy.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    private User currentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public OrderDto placeOrder(CreateOrderRequestDto request) {
        User user = currentUser();
        final ShoppingCart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(request.getShippingAddress());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setBook(cartItem.getBook());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getBook().getPrice());

            total = total.add(
                    item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );

            order.getOrderItems().add(item);
        }

        order.setTotal(total);

        cart.getCartItems().clear();
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getUserOrders() {
        return orderRepository.findAllByUser(currentUser())
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderMapper::toItemDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItem(Long orderId, Long itemId) {
        return orderMapper.toItemDto(
                orderItemRepository.findByIdAndOrderId(itemId, orderId)
                        .orElseThrow(() -> new EntityNotFoundException("Order item not found"))
        );
    }

    @Override
    public OrderDto updateStatus(Long id, UpdateOrderStatusRequestDto request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatus(request.getStatus());
        return orderMapper.toDto(order);
    }
}
