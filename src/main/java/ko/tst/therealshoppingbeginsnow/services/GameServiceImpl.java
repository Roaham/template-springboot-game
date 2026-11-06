package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.*;
import ko.tst.therealshoppingbeginsnow.exceptions.GameConflictException;
import ko.tst.therealshoppingbeginsnow.exceptions.GameNotFoundException;
import ko.tst.therealshoppingbeginsnow.mappers.GameMapper;
import ko.tst.therealshoppingbeginsnow.models.*;
import ko.tst.therealshoppingbeginsnow.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService{

    private static final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameRepository repository;
    private final PlatformRepository platformRepository;
    private final DeveloperRepository developerRepository;
    private final EditorRepository editorRepository;
    private final CategoryRepository categoryRepository;
    private final GameMapper mapper;

    public GameServiceImpl(
            GameRepository repository,
            PlatformRepository platformRepository,
            DeveloperRepository developerRepository,
            EditorRepository editorRepository,
            CategoryRepository categoryRepository,
            GameMapper mapper
    ) {
        this.repository = repository;
        this.platformRepository = platformRepository;
        this.developerRepository = developerRepository;
        this.editorRepository = editorRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }


    @Override
    @Transactional(readOnly = true)
    // i try to implement filters butttt ...
    public Page<GameResponse> findAll(Pageable pageable) {
        return repository.findAllByOrderByNameAsc(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public GameResponse findById(UUID id) {
        // not directly cuzz is an optional
        Game game = repository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game nor Found", id));

        return mapper.toResponse(game);
    }

    @Override
    @Transactional
    public GameResponse save(GameCreateRequest game) {
        if (repository.existsByNameIgnoreCase(game.name())) {
            throw new GameConflictException("Funko exist w/ name:" + game.name());
        }

        Set<UUID> platformIds = game.platformIds() == null ? Set.of() : game.platformIds();
        Set<UUID> developerIds = game.developerIds() == null ? Set.of() : game.developerIds();
        Set<UUID> editorIds = game.editorIds() == null ? Set.of() : game.editorIds();
        Set<UUID> categoryIds = game.categoryIds() == null ? Set.of() : game.categoryIds();

        Set<Platform> platforms = new HashSet<>(platformRepository.findAllById(platformIds));
        Set<Developer> developers = new HashSet<>(developerRepository.findAllById(developerIds));
        Set<Editor> editors = new HashSet<>(editorRepository.findAllById(editorIds));
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));

        Game newbie = mapper.toModel(
                game,
                platforms,
                developers,
                editors,
                categories
        );

        return mapper.toResponse(repository.save(newbie));
    }

    @Override
    @Transactional
    public List<GameResponse> saveAll(List<GameCreateRequest> games) {
        return games.stream().map(this::save).toList();
    }

    @Override
    @Transactional
    public GameResponse replace(UUID id, GameUpdateRequest game) {
        Set<UUID> platformIds = game.platformIds() == null ? Set.of() : game.platformIds();
        Set<UUID> developerIds = game.developerIds() == null ? Set.of() : game.developerIds();
        Set<UUID> editorIds = game.editorIds() == null ? Set.of() : game.editorIds();
        Set<UUID> categoryIds = game.categoryIds() == null ? Set.of() : game.categoryIds();

        Set<Platform> platforms = new HashSet<>(platformRepository.findAllById(platformIds));
        Set<Developer> developers = new HashSet<>(developerRepository.findAllById(developerIds));
        Set<Editor> editors = new HashSet<>(editorRepository.findAllById(editorIds));
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));

        Game original = repository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game nor Found", id));

        Game newbie = mapper.replace(
            original,
            game,
            platforms,
            developers,
            editors,
            categories
        );

        return mapper.toResponse(newbie);
    }

    @Override
    @Transactional
    public GameResponse update(UUID id, GameUpdateRequest game) {
        Set<UUID> platformIds = game.platformIds() == null ? Set.of() : game.platformIds();
        Set<UUID> developerIds = game.developerIds() == null ? Set.of() : game.developerIds();
        Set<UUID> editorIds = game.editorIds() == null ? Set.of() : game.editorIds();
        Set<UUID> categoryIds = game.categoryIds() == null ? Set.of() : game.categoryIds();

        Set<Platform> platforms = new HashSet<>(platformRepository.findAllById(platformIds));
        Set<Developer> developers = new HashSet<>(developerRepository.findAllById(developerIds));
        Set<Editor> editors = new HashSet<>(editorRepository.findAllById(editorIds));
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));

        Game original = repository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game nor Found", id));

        Game newbie = mapper.patch(
                original,
                game,
                platforms,
                developers,
                editors,
                categories
        );

        return mapper.toResponse(newbie);
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        repository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game nor Found", id));
        repository.logicDelete(id);
    }

    @Override
    // not implemented yet
    public StatsResponse stats() {
        return null;
    }
}
