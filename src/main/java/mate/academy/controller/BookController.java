package mate.academy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.dto.BookDto;
import mate.academy.dto.BookSearchParametersDto;
import mate.academy.dto.CreateBookRequestDto;
import mate.academy.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "API for managing books")
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    @Operation(summary = "Get all books")
    public Page<BookDto> getAll(
            @PageableDefault(size = 10, sort = "title")
            Pageable pageable) {
        return bookService.getAll(pageable);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create book")
    public BookDto createBook(
            @Valid @RequestBody CreateBookRequestDto bookDto) {
        return bookService.create(bookDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book")
    public BookDto updateBook(
            @PathVariable Long id,
            @Valid @RequestBody CreateBookRequestDto bookDto) {
        return bookService.update(id, bookDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a book")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/search")
    @Operation(summary = "Search books with filters")
    public Page<BookDto> searchBooks(
            @ModelAttribute BookSearchParametersDto searchParameters,
            @PageableDefault(size = 10)
            Pageable pageable) {
        return bookService.searchBooks(searchParameters, pageable);
    }
}
