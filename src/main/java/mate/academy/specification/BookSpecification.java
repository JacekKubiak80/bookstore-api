package mate.academy.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import mate.academy.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> withFilters(
            String[] titles,
            String[] authors,
            String[] isbns
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            addLike(predicates, cb, root.get("title"), titles);
            addLike(predicates, cb, root.get("author"), authors);
            addEqual(predicates, cb, root.get("isbn"), isbns);

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addLike(List<Predicate> predicates,
                                CriteriaBuilder cb,
                                Path<String> field,
                                String[] values) {

        if (values == null || values.length == 0) {
            return;
        }

        List<Predicate> orPredicates = new ArrayList<>();
        for (String value : values) {
            if (value == null || value.isEmpty()) {
                continue;
            }
            orPredicates.add(
                    cb.like(
                            cb.lower(field),
                            "%" + value.toLowerCase() + "%"
                    )
            );
        }

        predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
    }

    private static void addEqual(List<Predicate> predicates,
                                 CriteriaBuilder cb,
                                 Path<String> field,
                                 String[] values) {

        if (values == null || values.length == 0) {
            return;
        }

        List<Predicate> orPredicates = new ArrayList<>();
        for (String value : values) {
            if (value == null || value.isBlank()) {
                continue;
            }
            orPredicates.add(
                    cb.equal(
                            cb.lower(field),
                            value.toLowerCase()
                    )
            );
        }

        predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
    }
}
