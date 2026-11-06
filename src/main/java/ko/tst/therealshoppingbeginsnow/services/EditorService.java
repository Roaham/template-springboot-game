package ko.tst.therealshoppingbeginsnow.services;

import ko.tst.therealshoppingbeginsnow.dtos.*;

import java.util.List;
import java.util.UUID;

public interface EditorService {
    List<EditorResponse> findAll();
    EditorResponse findById(UUID id);
    EditorResponse save(EditorCreateRequest editor);
    List<EditorResponse> saveAll(List<EditorCreateRequest> editors);
    EditorResponse replace(UUID id, EditorUpdateRequest editor);
    EditorResponse update(UUID id, EditorUpdateRequest editor);
    void deleteById(UUID id);
}
