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
public class EventsServiceImpl implements EventsService{
    private final EventRepository eventRepository;

    @Override
    public List<EventDtoFull> getEvents(String text,
                                        Long[] categories,
                                        String paid,
                                        String rangeStart,
                                        String rangeEnd,
                                        String onlyAvailable,
                                        String sort,
                                        Integer from,
                                        Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime rangeStartFormat = LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime rangeEndFormat = LocalDateTime.parse(rangeEnd, formatter);

        if (rangeStartFormat.isAfter(rangeEndFormat)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Boolean isPaid = Boolean.parseBoolean(paid);
        Boolean isOnlyAvailable = Boolean.parseBoolean(onlyAvailable);
        List<Event> eventList = eventRepository.getEventsPublic(text, categories, isPaid, rangeStartFormat, rangeEndFormat, isOnlyAvailable, page).getContent();
        List<EventDtoFull> eventDtoFulls = new ArrayList<>();

        for (Event next: eventList) {
            eventDtoFulls.add(EventMapper.map(next));
        }

        return eventDtoFulls;
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
