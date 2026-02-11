package mate.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import mate.academy.dto.CategoryDto;
import mate.academy.mapper.CategoryMapper;
import mate.academy.model.Category;
import mate.academy.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getById_shouldReturnDto() {
        Category category = new Category();
        category.setId(1L);

        CategoryDto dto = new CategoryDto();
        dto.setId(1L);

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category))
                .thenReturn(dto);

        CategoryDto result = categoryService.getById(1L);

        assertEquals(1L, result.getId());
    }
}
