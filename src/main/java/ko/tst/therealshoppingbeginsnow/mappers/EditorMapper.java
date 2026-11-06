package ko.tst.therealshoppingbeginsnow.mappers;

import ko.tst.therealshoppingbeginsnow.dtos.*;
import ko.tst.therealshoppingbeginsnow.models.Editor;
import ko.tst.therealshoppingbeginsnow.models.Platform;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EditorMapper {
    public EditorResponse toResponse(Editor editor) {
        return new EditorResponse(
                editor.getId(),
                editor.getName(),
                new HashSet<>(editor.getPhotos())
        );
    }

    public Set<EditorResponse> toResponse(Set<Editor> editors) {
        // a collector that aim to a set, lol
        return editors.stream().map(this::toResponse).collect(Collectors.toSet());
    }

    public Editor toModel(EditorCreateRequest editor) {
        Editor model = new Editor(editor.name());
        if (editor.photos() != null) model.setPhotos(editor.photos());
        return model;
    }

    public Set<Editor> toModel(Set<EditorCreateRequest> editors) {
        return editors.stream().map(this::toModel).collect(Collectors.toSet());
    }

    public Editor replace(Editor original, EditorUpdateRequest editor) {
        // change the original directly
        original.setName(editor.name());
        if (editor.photos() != null) original.setPhotos(editor.photos());
        return original;
    }

    public Editor patch(Editor original, EditorUpdateRequest platform) {
        if (platform.name() != null) {
            original.setName(platform.name());
        } else {
            original.setName(original.getName());
        }
        if (platform.photos() != null) original.setPhotos(platform.photos());
        return original;
    }
}
