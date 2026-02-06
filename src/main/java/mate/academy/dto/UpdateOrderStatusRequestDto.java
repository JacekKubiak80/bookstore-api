package mate.academy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mate.academy.model.OrderStatus;

@Data
public class UpdateOrderStatusRequestDto {
    @NotNull
    private OrderStatus status;
}
