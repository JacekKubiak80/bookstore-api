package mate.academy.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mate.academy.dto.BookDtoWithoutCategoryIds;
import mate.academy.dto.CategoryDto;
import mate.academy.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    void getAll_shouldReturnPageWithContent() throws Exception {
        when(categoryService.findAll(any()))
                .thenReturn(new PageImpl<>(List.of(new CategoryDto())));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0]", notNullValue()));
    }

    @Test
    void getById_shouldReturnCategoryDto() throws Exception {
        when(categoryService.getById(1L))
                .thenReturn(new CategoryDto());

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void create_shouldReturnCreatedCategory() throws Exception {
        CategoryDto dto = new CategoryDto();
        when(categoryService.save(any()))
                .thenReturn(dto);

        mockMvc.perform(post("/api/categories")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void update_shouldReturnUpdatedCategory() throws Exception {
        CategoryDto dto = new CategoryDto();
        when(categoryService.update(eq(1L), any()))
                .thenReturn(dto);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        doNothing().when(categoryService).deleteById(1L);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getBooksByCategory_shouldReturnPageWithBooks() throws Exception {
        when(categoryService.getBooksByCategoryId(eq(1L), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new BookDtoWithoutCategoryIds())));

        mockMvc.perform(get("/api/categories/1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0]", notNullValue()));
    }
}
