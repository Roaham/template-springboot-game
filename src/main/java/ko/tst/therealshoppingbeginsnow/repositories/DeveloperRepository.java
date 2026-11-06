package ko.tst.therealshoppingbeginsnow.repositories;

import ko.tst.therealshoppingbeginsnow.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeveloperRepository extends JpaRepository<Developer, UUID> {

    Optional<Developer> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Developer> findAllByOrderByNameAsc();
}
