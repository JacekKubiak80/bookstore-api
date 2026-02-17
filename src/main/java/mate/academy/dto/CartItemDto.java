package mate.academy.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private int quantity;

    public CartItemDto(Long id, Long id1, Long id2, @Positive int quantity) {
    }

    public CartItemDto() {

    }
}

