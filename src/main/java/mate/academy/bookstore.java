package mate.academy;

import mate.academy.model.Book;
import mate.academy.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class bookstore {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {

        SpringApplication.run(bookstore.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book oldBook = new Book();
                oldBook.setTitle("Biblia");
                oldBook.setAuthor("John");

                bookService.save(oldBook);

                System.out.println(bookService.findAll());
            }
        };

    }
}

