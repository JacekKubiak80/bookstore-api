package mate.academy;

import java.math.BigDecimal;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BookService bookService) {
        return args -> {
            String isbn = "978-3-540-119010";

            boolean bookExists = bookService.findAll().stream()
                    .anyMatch(book -> isbn.equals(book.getIsbn()));

            if (!bookExists) {
                Book book = new Book();
                book.setTitle("Biblia");
                book.setAuthor("John");
                book.setIsbn(isbn);
                book.setPrice(BigDecimal.valueOf(200));
                bookService.save(book);
            }
        };
    }
}
