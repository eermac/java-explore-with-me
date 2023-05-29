package major.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import major.events.dto.EventDtoFull;
import major.events.mapper.EventMapper;
import major.events.model.Event;
import major.events.model.EventState;
import major.events.repository.EventRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventsServiceImpl implements EventsService {
    private final EventRepository eventRepository;

    @Override
    public List<EventDtoFull> getEvents(String text,
                                 List<Long> categories,
                                        String paid,
                                        String rangeStart,
                                        String rangeEnd,
                                        String onlyAvailable,
                                        String sort,
                                        Integer from,
                                        Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);

        LocalDateTime rangeStartFormat = null;
        LocalDateTime rangeEndFormat = null;

        Boolean isPaid = null;
        Boolean isAvailable = false;
        if (paid != null) isPaid = Boolean.parseBoolean(paid);
        if (onlyAvailable != null) isAvailable = Boolean.parseBoolean(onlyAvailable);


        if (rangeStart != null && rangeStart != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            rangeStartFormat = LocalDateTime.parse(rangeStart, formatter);
            rangeEndFormat = LocalDateTime.parse(rangeEnd, formatter);

            if (rangeStartFormat.isAfter(rangeEndFormat)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Event> eventList = eventRepository.getEventsPublic(text, categories, isPaid, rangeStartFormat, rangeEndFormat, isAvailable, sort, page).getContent();

        List<EventDtoFull> dto = new ArrayList<>();

        for (Event next: eventList) {
            dto.add(EventMapper.map(next));
        }

        return dto;
    }

    @Override
    public EventDtoFull getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).get();

        if (event.getState().equals(EventState.PUBLISHED)) {
            event.setViews(event.getViews() + 1);
            return EventMapper.map(event);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
