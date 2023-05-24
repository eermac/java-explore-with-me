package major.compilations.mapper;

import major.compilations.dto.CompilationsDto;
import major.compilations.model.Compilations;
import major.events.model.Event;

import java.util.List;

public class CompilationsMapper {
    public static Compilations map(CompilationsDto dto, List<Event> events) {
        if (dto.getPinned() == null) dto.setPinned(false);
        return new Compilations(null,
                events,
                dto.getPinned(),
                dto.getTitle());
    }
}