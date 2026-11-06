package ko.tst.therealshoppingbeginsnow.repositories;

import ko.tst.therealshoppingbeginsnow.models.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EditorRepository extends JpaRepository<Editor, UUID> {

    Optional<Editor> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Editor> findAllByOrderByNameAsc();
}
