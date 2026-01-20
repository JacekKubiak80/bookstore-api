package mate.academy.service;

import mate.academy.dto.BookDto;
import mate.academy.dto.BookSearchParametersDto;
import mate.academy.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookDto> getAll(Pageable pageable);

    BookDto getById(Long id);

    BookDto create(CreateBookRequestDto bookDto);

    BookDto update(Long id, CreateBookRequestDto bookDto);

    void delete(Long id);

    Page<BookDto> searchBooks(BookSearchParametersDto searchParametersDto,
                              Pageable pageable);
}
