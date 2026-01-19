package mate.academy.specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import mate.academy.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> withFilters(String[] titles,
                                                  String[] authors, String[] isbns) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (titles != null && titles.length > 0) {
                List<Predicate> titlePredicates = new ArrayList<>();
                for (String title : titles) {
                    titlePredicates.add(cb.like(cb.lower(root.get("title")),
                            "%" + title.toLowerCase() + "%"));
                }
                predicates.add(cb.or(titlePredicates.toArray(new Predicate[0])));
            }

            if (authors != null && authors.length > 0) {
                List<Predicate> authorPredicates = new ArrayList<>();
                for (String author : authors) {
                    authorPredicates.add(cb.like(cb.lower(root.get("author")),
                            "%" + author.toLowerCase() + "%"));
                }
                predicates.add(cb.or(authorPredicates.toArray(new Predicate[0])));
            }

            if (isbns != null && isbns.length > 0) {
                List<Predicate> isbnPredicates = new ArrayList<>();
                for (String isbn : isbns) {
                    isbnPredicates.add(cb.equal(cb.lower(root.get("isbn")), isbn.toLowerCase()));
                }
                predicates.add(cb.or(isbnPredicates.toArray(new Predicate[0])));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

