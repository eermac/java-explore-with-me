package major.compilations.service;

import lombok.extern.slf4j.Slf4j;
import major.compilations.dto.CompilationsDto;
import major.compilations.mapper.CompilationsMapper;
import major.compilations.model.Compilations;
import major.compilations.repository.CompilationsRepository;
import lombok.RequiredArgsConstructor;
import major.events.model.Event;
import major.events.repository.EventRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationsServiceImpl implements CompilationsService {
    private final CompilationsRepository repository;
    private final EventRepository eventRepository;

    @Override
    public Compilations add(CompilationsDto dto) {
        Set<Event> events = eventRepository.getEventsById(dto.getEvents());
        return repository.save(CompilationsMapper.map(dto, events));
    }

    @Override
    public void delete(Long compId) {
        repository.deleteById(compId);
    }

    @Override
    public Compilations update(Long compId, CompilationsDto dto) {
        Compilations oldCompilation = repository.findById(compId).get();

        if (dto.getTitle() != null) oldCompilation.setTitle(dto.getTitle());
        if (dto.getPinned() != null) oldCompilation.setPinned(dto.getPinned());
        if (dto.getEvents() != null) {
            Set<Event> events = eventRepository.getEventsById(dto.getEvents());
            oldCompilation.setEvents(events);
        }

        return repository.save(oldCompilation);
    }

    @Override
    public List<Compilations> getAll(Boolean pinned, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return repository.getCompilations(pinned, page).getContent();
    }

    @Override
    public Compilations get(Long compId) {
        return repository.findById(compId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}