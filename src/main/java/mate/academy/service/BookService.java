package mate.academy.service;

import java.util.List;
import mate.academy.dto.BookDto;
import mate.academy.dto.BookSearchParametersDto;
import mate.academy.dto.CreateBookRequestDto;

public interface BookService {

    List<BookDto> getAll();

    BookDto getById(Long id);

    BookDto create(CreateBookRequestDto bookDto);

    BookDto update(Long id, CreateBookRequestDto bookDto);

    void delete(Long id);

    List<BookDto> searchBooks(BookSearchParametersDto searchParametersDto);
}
