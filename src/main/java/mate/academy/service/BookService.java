package mate.academy.service;

import java.util.List;
import mate.academy.dto.BookDto;
import mate.academy.dto.CreateBookRequestDto;
import mate.academy.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();

    List<BookDto> getAll();

    BookDto getById(Long id);

    BookDto create(CreateBookRequestDto bookDto);
}
