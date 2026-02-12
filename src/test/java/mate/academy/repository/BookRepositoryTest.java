package mate.academy.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import mate.academy.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void existsByIsbn_shouldReturnTrue() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setIsbn("123-456");
        book.setPrice(BigDecimal.TEN);
        bookRepository.save(book);
        assertTrue(bookRepository.existsByIsbn("123-456"));    }

    @Test
    void findAllByCategoriesId_shouldReturnPage() {
        Book book = new Book();
        book.setTitle("Category Test Book");
        book.setAuthor("Author");
        book.setIsbn("999");
        book.setPrice(BigDecimal.ONE);

        bookRepository.save(book);

        Page<Book> page = bookRepository.findAllByCategories_Id(0L, PageRequest.of(0, 10));
        assertNotNull(page);    }

    @Test
    void saveAndRetrieve_shouldPersistBook() {
        Book book = new Book();
        book.setTitle("Persisted Book");
        book.setAuthor("Author");
        book.setIsbn("777");
        book.setPrice(BigDecimal.valueOf(15.5));

        Book saved = bookRepository.save(book);
        assertNotNull(saved.getId());

        Book retrieved = bookRepository.findById(saved.getId()).orElse(null);
        assertNotNull(retrieved);
    }
}
