package mate.academy.service;

import java.util.List;
import mate.academy.dto.BookDtoWithoutCategoryIds;
import mate.academy.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CategoryDto dto);

    CategoryDto update(Long id, CategoryDto dto);

    void deleteById(Long id);

    Page<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable);
}
