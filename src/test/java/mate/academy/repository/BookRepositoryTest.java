package mate.academy.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Set;
import mate.academy.model.Book;
import mate.academy.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void existsByIsbn_shouldReturnTrue() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setIsbn("123-456");
        book.setPrice(BigDecimal.TEN);

        bookRepository.save(book);

        assertTrue(bookRepository.existsByIsbn("123-456"));
    }

    @Test
    void existsByIsbn_shouldReturnFalseForUnknownIsbn() {
        assertTrue(!bookRepository.existsByIsbn("non-existent-isbn"));
    }

    @Test
    void findAllByCategoriesId_shouldReturnPage() {
        // Tworzymy kategorię
        Category category = new Category();
        category.setName("Fiction");
        category = categoryRepository.save(category);

        // Tworzymy książkę i przypisujemy kategorię
        Book book = new Book();
        book.setTitle("Category Test Book");
        book.setAuthor("Author");
        book.setIsbn("999");
        book.setPrice(BigDecimal.ONE);
        book.setCategories(Set.of(category));

        bookRepository.save(book);

        // Test repozytorium z paginacją
        Page<Book> page = bookRepository.findAllByCategories_Id(category.getId(), PageRequest.of(0, 10));
        assertNotNull(page);
        assertTrue(page.getContent().size() > 0);
        assertTrue(page.getContent().get(0).getCategories().contains(category));
    }

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

    @Test
    void saveBookWithCategory_shouldPersistRelation() {
        // Tworzymy kategorię
        Category category = new Category();
        category.setName("Science");
        category = categoryRepository.save(category);

        // Tworzymy książkę i przypisujemy kategorię
        Book book = new Book();
        book.setTitle("Science Book");
        book.setAuthor("Author");
        book.setIsbn("555");
        book.setPrice(BigDecimal.valueOf(20));
        book.setCategories(Set.of(category));

        Book saved = bookRepository.save(book);
        assertNotNull(saved.getId());
        assertTrue(saved.getCategories().contains(category));

        // Pobieramy książkę i sprawdzamy relację
        Book retrieved = bookRepository.findById(saved.getId()).orElse(null);
        assertNotNull(retrieved);
        assertTrue(retrieved.getCategories().contains(category));
    }
}
