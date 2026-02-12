package mate.academy.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mate.academy.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void save_shouldPersistCategory() {
        Category category = new Category();
        category.setName("Fantasy");

        Category saved = categoryRepository.save(category);
        assertNotNull(saved.getId());
    }

    @Test
    void findById_shouldReturnCategory() {
        Category category = new Category();
        category.setName("Science");

        Category saved = categoryRepository.save(category);
        Category retrieved = categoryRepository.findById(saved.getId()).orElse(null);

        assertNotNull(retrieved);
    }

    @Test
    void deleteById_shouldRemoveCategory() {
        Category category = new Category();
        category.setName("History");

        Category saved = categoryRepository.save(category);

        categoryRepository.deleteById(saved.getId());

        assertTrue(categoryRepository.findById(saved.getId()).isEmpty());
    }
}
