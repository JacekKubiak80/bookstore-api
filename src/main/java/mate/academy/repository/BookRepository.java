package mate.academy.repository;

import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final SessionFactory sessionFactory;

    public Book save(Book book) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Could not save book: " + book.getTitle(), e);
        }
    }

    public List<Book> findAll() {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            CriteriaQuery<Book> query = session
                    .getCriteriaBuilder()
                    .createQuery(Book.class);
            query.from(Book.class);

            List<Book> result = session.createQuery(query).getResultList();

            transaction.commit();
            return result;

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Could not load list of books", e);
        }
    }

    public Optional<Book> findById(Long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Book book = session.get(Book.class, id); // pobranie po PK

            transaction.commit();
            return Optional.ofNullable(book);

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Could not find book with id: " + id, e);
        }
    }
}

