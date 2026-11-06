package ko.tst.therealshoppingbeginsnow.repositories;

import ko.tst.therealshoppingbeginsnow.models.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID>, JpaSpecificationExecutor<Game> {
    // find by name
    Optional<Game> findByNameIgnoreCase(String name);
    // exist by name
    boolean existsByNameIgnoreCase(String name);
    //find by name
    Page<Game> findByNameContainingIgnoreCase(String name, Pageable pageable);
    // find by mark
    Page<Game> findByMarkGreaterThanEqual(Integer mark, Pageable pageable);
    // find by order asc
    Page<Game> findAllByOrderByNameAsc(Pageable pageable);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
    UPDATE Game f
        SET f.deleted = true
    WHERE f.id = :id AND f.deleted = false
    """)
    boolean logicDelete(@Param("id") UUID id);
}