package mate.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import mate.academy.dto.BookDto;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.BookMapper;
import mate.academy.model.Book;
import mate.academy.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getById_shouldReturnBookDto() {
        Book book = new Book();
        book.setId(1L);

        BookDto dto = new BookDto();
        dto.setId(1L);

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));
        when(bookMapper.toDto(book))
                .thenReturn(dto);

        BookDto result = bookService.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {
        when(bookRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> bookService.getById(1L)
        );
    }
}
