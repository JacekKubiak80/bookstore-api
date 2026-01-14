package mate.academy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank(message = "Title must not be blank")
    private String title;
    @NotBlank(message = "Author must not be blank")
    private String author;
    @NotBlank(message = "ISBN must not be blank")
    private String isbn;
    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    @NotBlank(message = "Description must not be blank")
    private String description;
    private String coverImage;
}
