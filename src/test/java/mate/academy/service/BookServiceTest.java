package mate.academy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import mate.academy.dto.BookDto;
import mate.academy.dto.CreateBookRequestDto;
import mate.academy.exception.DuplicateResourceException;
import mate.academy.mapper.BookMapper;
import mate.academy.model.Book;
import mate.academy.repository.BookRepository;
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
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getAll_shouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(List.of(new Book()));

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(any())).thenReturn(new BookDto());

        Page<BookDto> result = bookService.getAll(pageable);

        assertEquals(1, result.getContent().size());
        verify(bookRepository).findAll(pageable);
    }

    @Test
    void create_shouldReturnSavedDto() {
        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setIsbn("123");

        Book book = new Book();
        Book saved = new Book();
        saved.setId(1L);

        when(bookMapper.toEntity(request)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(saved);
        when(bookMapper.toDto(saved)).thenReturn(new BookDto());

        BookDto result = bookService.create(request);

        assertNotNull(result);
        verify(bookRepository).save(book);
    }

    @Test
    void update_shouldUpdateBook() {
        Book existing = new Book();
        existing.setId(1L);
        existing.setIsbn("123");

        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setIsbn("123");
        request.setTitle("New");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(existing)).thenReturn(existing);
        when(bookMapper.toDto(existing)).thenReturn(new BookDto());

        BookDto result = bookService.update(1L, request);

        assertNotNull(result);
        verify(bookRepository).save(existing);
    }

    @Test
    void update_shouldThrowException_whenDuplicateIsbn() {
        Book existing = new Book();
        existing.setId(1L);
        existing.setIsbn("123");

        CreateBookRequestDto request = new CreateBookRequestDto();
        request.setIsbn("999");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.existsByIsbn("999")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> bookService.update(1L, request));
    }

    @Test
    void delete_shouldCallRepository() {
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.delete(1L);

        verify(bookRepository).delete(book);
    }
}
