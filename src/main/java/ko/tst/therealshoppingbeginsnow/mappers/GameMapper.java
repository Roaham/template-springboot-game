package ko.tst.therealshoppingbeginsnow.mappers;

import ko.tst.therealshoppingbeginsnow.dtos.GameCreateRequest;
import ko.tst.therealshoppingbeginsnow.dtos.GameResponse;
import ko.tst.therealshoppingbeginsnow.dtos.GameUpdateRequest;
import ko.tst.therealshoppingbeginsnow.models.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GameMapper {
    // hay que crear primero los otros mappers :(

    private final PlatformMapper platformMapper;
    private final DeveloperMapper developerMapper;
    private final EditorMapper editorMapper;
    private final CategoryMapper categoryMapper;

    public GameMapper(
            PlatformMapper platformMapper,
            DeveloperMapper developerMapper,
            EditorMapper editorMapper,
            CategoryMapper categoryMapper
    ) {
        this.platformMapper = platformMapper;
        this.developerMapper = developerMapper;
        this.editorMapper = editorMapper;
        this.categoryMapper = categoryMapper;
    }

    public GameResponse toResponse(Game game) {
        return new GameResponse(
                game.getId(),
                game.getName(),
                game.getDescription(),
                platformMapper.toResponse(game.getPlatforms()),
                developerMapper.toResponse(game.getDevelopers()),
                editorMapper.toResponse(game.getEditors()),
                categoryMapper.toResponse(game.getCategories()),
                new HashSet<>(game.getPhotos()),
                game.getMark(),
                game.getCreatedAt(),
                game.getUpdatedAt()
        );
    }

    public List<GameResponse> toResponse(List<Game> games) {
        return games.stream().map(this::toResponse).toList();
    }

    public Game toModel(
            GameCreateRequest game,
            Set<Platform> platforms,
            Set<Developer> developers,
            Set<Editor> editors,
            Set<Category> categories
    ) {
        Game newbie = new Game(
                game.name(),
                game.description()
        );
        newbie.setPlatforms(platforms);
        newbie.setDevelopers(developers);
        newbie.setEditors(editors);
        newbie.setCategories(categories);
        newbie.setPhotos(game.photos() == null ? new java.util.HashSet<>() : game.photos());
        newbie.setMark(game.mark());

        return newbie;
    }

    public Game replace(
            Game original,
            GameUpdateRequest game,
            Set<Platform> platforms,
            Set<Developer> developers,
            Set<Editor> editors,
            Set<Category> categories
    ) {
        original.setName(game.name());
        original.setDescription(game.description());
        original.setPlatforms(platforms);
        original.setDevelopers(developers);
        original.setEditors(editors);
        original.setCategories(categories);
        if (game.photos() != null) {
            original.setPhotos(game.photos());
        }
        original.setMark(game.mark());

        return original;
    }

    public Game patch(
            Game original,
            GameUpdateRequest game,
            Set<Platform> platforms,
            Set<Developer> developers,
            Set<Editor> editors,
            Set<Category> categories
    ) {
        if (game.name() != null) {
            original.setName(game.name());
        }
        if (game.description() != null) {
            original.setDescription(game.description());
        }
        if (game.mark() != null) {
            original.setMark(game.mark());
        }
        if (platforms != null) {
            original.setPlatforms(platforms);
        }
        if (developers != null) {
            original.setDevelopers(developers);
        }
        if (editors != null) {
            original.setEditors(editors);
        }
        if (categories != null) {
            original.setCategories(categories);
        }
        if (game.photos() != null) {
            original.setPhotos(game.photos());
        }

        return original;
    }

}
