package mate.academy.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
