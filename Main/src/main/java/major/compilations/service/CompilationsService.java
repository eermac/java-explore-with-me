package major.compilations.service;

import major.compilations.dto.CompilationsDto;
import major.compilations.model.Compilations;

import java.util.List;

public interface CompilationsService {
    Compilations add(CompilationsDto dto);

    void delete(Long compId);

    Compilations update(Long compId, CompilationsDto dto);

    List<Compilations> getAll(Boolean pinned, Integer from, Integer size);

    Compilations get(Long compId);
}
