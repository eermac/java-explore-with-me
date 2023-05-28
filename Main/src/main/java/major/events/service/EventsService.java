package major.events.service;

import major.events.dto.EventDtoFull;
import major.events.model.Event;

import java.util.List;

public interface EventsService {
    List<Event> getEvents(String text,
                          Long[] categories,
                          String paid,
                          String rangeStart,
                          String rangeEnd,
                          String onlyAvailable,
                          String sort,
                          Integer from,
                          Integer size);

    EventDtoFull getEvent(Long id);
}
