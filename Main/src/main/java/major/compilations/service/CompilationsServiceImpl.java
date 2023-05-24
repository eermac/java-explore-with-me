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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationsServiceImpl implements CompilationsService{
    private final CompilationsRepository repository;
    private final EventRepository eventRepository;

    @Override
    public Compilations add(CompilationsDto dto) {
        List<Event> events = eventRepository.getEventsById(dto.getEvents());
        return repository.save(CompilationsMapper.map(dto, events));
    }

    @Override
    public void delete(Long compId) {
        repository.deleteById(compId);
    }

    @Override
    public Compilations update(Long compId, CompilationsDto dto) {
        log.info("\n!!!!Обновление подборки\n");
        List<Event> events = eventRepository.getEventsById(dto.getEvents());
        Compilations updateCompilations = CompilationsMapper.map(dto, events);
        updateCompilations.setId(compId);
        log.info("\n!!!Обновление подборки\n");
        return repository.save(updateCompilations);
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
