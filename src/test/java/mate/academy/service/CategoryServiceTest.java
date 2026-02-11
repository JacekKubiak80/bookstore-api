package mate.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void findAll_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> page = new PageImpl<>(List.of(new Category()));

        when(categoryRepository.findAll(pageable)).thenReturn(page);
        when(categoryMapper.toDto(any())).thenReturn(new CategoryDto());

        Page<CategoryDto> result = categoryService.findAll(pageable);

        assertEquals(1, result.getContent().size());
    }

    @Test
    void save_shouldPersistCategory() {
        CategoryDto dto = new CategoryDto();
        Category category = new Category();

        when(categoryMapper.toEntity(dto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(dto);

        CategoryDto result = categoryService.save(dto);

        assertNotNull(result);
    }

    @Test
    void update_shouldModifyCategory() {
        Category category = new Category();
        category.setId(1L);

        CategoryDto dto = new CategoryDto();
        dto.setName("Updated");

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category))
                .thenReturn(dto);

        CategoryDto result = categoryService.update(1L, dto);

        assertEquals("Updated", result.getName());
    }

    @Test
    void delete_shouldCallRepository() {
        categoryService.deleteById(1L);

        verify(categoryRepository).deleteById(1L);
    }
}
