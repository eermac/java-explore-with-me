package major.events.service;

import major.events.dto.EventDtoFull;

import java.util.List;

public interface EventsService {
    List<EventDtoFull> getEvents(String text,
                          List<Long> categories,
                          String paid,
                          String rangeStart,
                          String rangeEnd,
                          String onlyAvailable,
                          String sort,
                          Integer from,
                          Integer size);

    EventDtoFull getEvent(Long id);
}
