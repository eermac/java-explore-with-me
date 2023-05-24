package major.events.mapper;

import major.categories.model.Categories;
import major.events.dto.EventDto;
import major.events.dto.EventDtoFull;
import major.events.model.Event;
import major.events.model.EventState;
import major.users.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EventMapper {
    public static Event map(EventDto dto, User user, Categories categories) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new Event(null,
                dto.getTitle(),
                dto.getAnnotation(),
                categories,
                dto.getPaid(),
                LocalDateTime.parse(dto.getEventDate(), formatter),
                user,
                dto.getDescription(),
                dto.getParticipantLimit(),
                EventState.PENDING,
                LocalDateTime.now(),
                dto.getLocation(),
                dto.getRequestModeration(),
                0L,
                0L);
    }

    public static EventDtoFull map(Event event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new EventDtoFull(event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getCategory(),
                event.getPaid(),
                event.getEventDate().format(formatter),
                event.getInitiator(),
                event.getDescription(),
                event.getParticipantLimit(),
                event.getState(),
                event.getCreatedOn(),
                event.getLocation(),
                event.getRequestModeration(),
                event.getConfirmedRequests(),
                event.getViews());
    }
}