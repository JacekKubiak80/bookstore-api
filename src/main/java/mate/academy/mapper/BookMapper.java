package mate.academy.mapper;

import java.util.stream.Collectors;
import mate.academy.dto.BookDto;
import mate.academy.dto.BookDtoWithoutCategoryId;
import mate.academy.dto.CreateBookRequestDto;
import mate.academy.model.Book;
import mate.academy.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto dto);

    BookDtoWithoutCategoryId toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(
            @MappingTarget BookDto bookDto,
            Book book) {

        bookDto.setCategoryIds(
                book.getCategories()
                        .stream()
                        .map(Category::getId)
                        .collect(Collectors.toSet())
        );
    }
}
