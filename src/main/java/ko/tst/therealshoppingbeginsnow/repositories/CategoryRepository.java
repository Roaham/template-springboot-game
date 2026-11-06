package ko.tst.therealshoppingbeginsnow.repositories;

import ko.tst.therealshoppingbeginsnow.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // find by name
    Optional<Category> findByNameIgnoreCase(String name);
    // exist by name
    boolean existsByNameIgnoreCase(String name);
    // fins all by order asc
    List<Category> findAllByOrderByNameAsc();
}
