package mate.academy.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import mate.academy.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void existsByIsbn_shouldReturnTrue() {
        Book book = new Book();
        book.setTitle("Test");
        book.setAuthor("Author");
        book.setIsbn("123");
        book.setPrice(BigDecimal.TEN);

        bookRepository.save(book);

        assertTrue(bookRepository.existsByIsbn("123"));
    }
}
