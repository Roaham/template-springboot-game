package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.*;
import ko.tst.therealshoppingbeginsnow.models.Category;
import ko.tst.therealshoppingbeginsnow.models.Developer;
import ko.tst.therealshoppingbeginsnow.models.Editor;
import ko.tst.therealshoppingbeginsnow.models.Platform;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameService {
    Page<GameResponse> findAll(Pageable pageable);
    GameResponse findById(UUID id);
    GameResponse save(GameCreateRequest game);
    List<GameResponse> saveAll(List<GameCreateRequest> games);
    GameResponse replace(UUID id, GameUpdateRequest game);
    GameResponse update(UUID id, GameUpdateRequest game);
    void deleteById(UUID id);
    StatsResponse stats();
}
