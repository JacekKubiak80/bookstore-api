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

    private final BookService bookService;

    public BookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book oldBook = new Book();
            oldBook.setTitle("Biblia");
            oldBook.setAuthor("John");
            oldBook.setIsbn("978-3-540-119010");
            oldBook.setPrice(BigDecimal.valueOf(200));
            bookService.save(oldBook);
            System.out.println(bookService.findAll());
        };
    }
}
