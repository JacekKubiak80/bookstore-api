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
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import mate.academy.dto.BookDto;
import mate.academy.dto.BookSearchParametersDto;
import mate.academy.dto.CreateBookRequestDto;
import mate.academy.security.JwtAuthenticationFilter;
import mate.academy.security.JwtUtil;
import mate.academy.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private BookDto createSampleBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("John Doe");
        bookDto.setIsbn("1234567890");
        bookDto.setPrice(BigDecimal.TEN);
        bookDto.setDescription("Sample description");
        bookDto.setCoverImage("image.png");
        bookDto.setCategoryIds(List.of(1L, 2L));
        return bookDto;
    }

    @Test
    void getAll_shouldReturnPageWithContent() throws Exception {
        when(bookService.getAll(any()))
                .thenReturn(new PageImpl<>(List.of(createSampleBookDto())));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0]", notNullValue()));
    }

    @Test
    void getById_shouldReturnBookDto() throws Exception {
        when(bookService.getById(1L))
                .thenReturn(createSampleBookDto());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void create_shouldReturnCreatedBook() throws Exception {
        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setTitle("Test Book");
        request.setAuthor("Author");
        request.setIsbn("123-456");
        request.setPrice(BigDecimal.TEN);

        when(bookService.create(any())).thenReturn(createSampleBookDto());

        mockMvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void update_shouldReturnUpdatedBook() throws Exception {
        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setTitle("Updated Book");
        request.setAuthor("Author");
        request.setIsbn("123-456");
        request.setPrice(BigDecimal.TEN);

        when(bookService.update(eq(1L), any())).thenReturn(createSampleBookDto());

        mockMvc.perform(put("/api/books/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        doNothing().when(bookService).delete(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void search_shouldReturnPageWithBooks() throws Exception {
        when(bookService.searchBooks(any(BookSearchParametersDto.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(createSampleBookDto())));

        mockMvc.perform(get("/api/books/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0]", notNullValue()));
    }
}
