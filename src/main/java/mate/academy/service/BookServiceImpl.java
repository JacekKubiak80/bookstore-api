package mate.academy.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.BookDto;
import mate.academy.dto.BookSearchParametersDto;
import mate.academy.dto.CreateBookRequestDto;
import mate.academy.exception.DuplicateResourceException;
import mate.academy.exception.EntityNotFoundException;
import mate.academy.mapper.BookMapper;
import mate.academy.model.Book;
import mate.academy.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id " + id));
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional
    public BookDto create(CreateBookRequestDto bookDto) {
        Book book = bookMapper.toModel(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto update(Long id, CreateBookRequestDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book not found with id " + id));

        String oldIsbn = book.getIsbn();
        String newIsbn = bookDto.getIsbn();

        if (!oldIsbn.equals(newIsbn) && bookRepository.existsByIsbn(newIsbn)) {
            throw new DuplicateResourceException("ISBN already exists: " + newIsbn);
        }

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());
        book.setDescription(bookDto.getDescription());
        book.setCoverImage(bookDto.getCoverImage());

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Book not found with id " + id));
        bookRepository.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> searchBooks(BookSearchParametersDto searchParametersDto) {
        return bookRepository.findAll().stream()
                .filter(book -> {
                    boolean matches = true;
                    if (searchParametersDto.title() != null
                            && !searchParametersDto.title().isBlank()) {
                        matches &= book.getTitle().toLowerCase()
                                .contains(searchParametersDto.title().toLowerCase());
                    }
                    if (searchParametersDto.author() != null
                            && !searchParametersDto.author().isBlank()) {
                        matches &= book.getAuthor().toLowerCase()
                                .contains(searchParametersDto.author().toLowerCase());
                    }
                    if (searchParametersDto.isbn() != null
                            && !searchParametersDto.isbn().isBlank()) {
                        matches &= book.getIsbn().equalsIgnoreCase(searchParametersDto.isbn());
                    }
                    return matches;
                })
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
}

