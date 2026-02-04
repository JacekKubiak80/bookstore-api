package mate.academy.mapper;

import mate.academy.dto.CategoryDto;
import mate.academy.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto dto);
}

