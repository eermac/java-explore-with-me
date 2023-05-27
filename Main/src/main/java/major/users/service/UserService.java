package major.users.service;

import major.events.dto.EventDto;
import major.events.dto.EventDtoFull;
import major.events.dto.EventDtoState;
import major.events.dto.RequestsStatus;
import major.events.model.Event;
import major.requests.dto.RequestDto;
import major.users.dto.UserDto;
import major.users.model.User;

import java.util.List;

public interface UserService {
    List<Event> getAllEventsForUser(Long userId, Integer from, Integer size);

    EventDtoFull addEventOnUser(Long userId, EventDto dto);

    Event getEventForUser(Long userId, Long eventId);

    EventDtoFull updateEventOnUser(Long userId, Long eventId, EventDtoState dto);

    List<RequestDto> getRequestsForUserOnEvent(Long userId, Long eventId);

    List<RequestDto> updateRequestsForUserOnEvent(Long userId, Long eventId, RequestsStatus ids);

    List<RequestDto> getRequestsForUser(Long userId);

    RequestDto addRequestOnUser(Long userId, Long eventId);

    RequestDto cancelRequestOnUser(Long userId, Long requestId);

    List<User> getUsersOnAdmin(Long[] users, Integer from, Integer size);

    User addUserOnAdmin(UserDto dto);

    void deleteUserOnAdmin(Long userID);

    EventDtoFull updateEventOnAdmin(Long eventId, EventDtoState dto);

    List<EventDtoFull> getEventsForAdmin(Long[] users, String states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size);
}
