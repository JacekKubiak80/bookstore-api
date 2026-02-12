package mate.academy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.dto.BookDto;
import mate.academy.dto.BookSearchParametersDto;
import mate.academy.dto.CreateBookRequestDto;
import mate.academy.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
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

    @Test
    void getAll_shouldReturn200() throws Exception {
        when(bookService.getAll(any()))
                .thenReturn(new PageImpl<>(List.of(new BookDto())));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        when(bookService.getById(1L))
                .thenReturn(new BookDto());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk());
    }

    @Test
    void create_shouldReturn201() throws Exception {
        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setTitle("Test");
        request.setAuthor("Author");
        request.setIsbn("123");
        request.setPrice(BigDecimal.TEN);

        when(bookService.create(any())).thenReturn(new BookDto());

        mockMvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void update_shouldReturn200() throws Exception {
        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setTitle("Updated");
        request.setAuthor("Author");
        request.setIsbn("123");
        request.setPrice(BigDecimal.TEN);

        when(bookService.update(eq(1L), any()))
                .thenReturn(new BookDto());

        mockMvc.perform(put("/api/books/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        doNothing().when(bookService).delete(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void search_shouldReturn200() throws Exception {
        when(bookService.searchBooks(any(BookSearchParametersDto.class), any()))
                .thenReturn(new PageImpl<>(List.of(new BookDto())));

        mockMvc.perform(get("/api/books/search"))
                .andExpect(status().isOk());
    }
}
