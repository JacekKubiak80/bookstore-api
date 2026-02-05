package mate.academy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddCartItemRequestDto {
    @NotNull
    private Long bookId;

    @Positive
    private int quantity;
}

