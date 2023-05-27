package major.events.mapper;

import lombok.extern.slf4j.Slf4j;
import major.categories.model.Categories;
import major.events.dto.EventDto;
import major.events.dto.EventDtoFull;
import major.events.dto.EventDtoState;
import major.events.model.Event;
import major.events.model.EventState;
import major.users.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
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
                LocalDateTime.parse(dto.getEventDate(), formatter).minusYears(100),
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
                event.getEventDate().toString().replace("T", " "),
                event.getInitiator(),
                event.getDescription(),
                event.getParticipantLimit(),
                event.getState(),
                event.getCreatedOn().format(formatter).replace("T", " "),
                event.getLocation(),
                event.getRequestModeration(),
                event.getPublishedOn().format(formatter).replace("T", " "),
                event.getConfirmedRequests(),
                event.getViews());
    }

    public static Event map(Event event, EventDtoState dto, Categories categories) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (dto.getAnnotation() != null)  event.setAnnotation(dto.getAnnotation());
        if (dto.getCategory() != null) event.setCategory(categories);
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null) event.setEventDate(LocalDateTime.parse(dto.getEventDate(), formatter));
        if (dto.getLocation() != null) event.setLocation(dto.getLocation());
        if (dto.getPaid() != null) event.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) event.setParticipantLimit(dto.getParticipantLimit());
        if (dto.getRequestModeration() != null) event.setRequestModeration((dto.getRequestModeration()));
        if (dto.getTitle() != null) event.setTitle(dto.getTitle());

        return event;
    }

    public static Event map(EventDtoFull dto, Event event, Categories categories) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (dto.getEventDate() != null && dto.getEventDate().contains("T")) dto.setEventDate(dto.getEventDate().replace("T", " "));

        if (dto.getAnnotation() != null)  event.setAnnotation(dto.getAnnotation());
        if (dto.getCategory() != null) event.setCategory(categories);
        if (dto.getDescription() != null) event.setDescription(dto.getDescription());
        if (dto.getEventDate() != null) event.setEventDate(LocalDateTime.parse(dto.getEventDate(), formatter));
        if (dto.getLocation() != null) event.setLocation(dto.getLocation());
        if (dto.getPaid() != null) event.setPaid(dto.getPaid());
        if (dto.getParticipantLimit() != null) event.setParticipantLimit(dto.getParticipantLimit());
        if (dto.getRequestModeration() != null) event.setRequestModeration((dto.getRequestModeration()));
        if (dto.getTitle() != null) event.setTitle(dto.getTitle());

        return new Event(event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getCategory(),
                event.getPaid(),
                event.getEventDate(),
                event.getInitiator(),
                event.getDescription(),
                event.getParticipantLimit(),
                event.getState(),
                event.getCreatedOn(),
                event.getLocation(),
                event.getRequestModeration(),
                event.getPublishedOn(),
                event.getConfirmedRequests(),
                event.getViews());
    }
}