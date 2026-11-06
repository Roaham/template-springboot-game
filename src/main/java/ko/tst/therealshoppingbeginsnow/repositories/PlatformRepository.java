package ko.tst.therealshoppingbeginsnow.repositories;

import ko.tst.therealshoppingbeginsnow.models.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlatformRepository extends JpaRepository<Platform, UUID> {

    Optional<Platform> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Platform> findAllByOrderByNameAsc();

    Page<Platform> findAllByOrderByNameAsc(Pageable pageable);
}
