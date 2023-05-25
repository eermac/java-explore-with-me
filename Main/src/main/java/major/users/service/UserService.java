package major.users.service;

import major.events.dto.EventDto;
import major.events.dto.EventDtoFull;
import major.events.dto.EventDtoState;
import major.events.model.Event;
import major.requests.dto.RequestDto;
import major.requests.model.Request;
import major.users.dto.UserDto;
import major.users.model.User;

import java.util.List;

public interface UserService {
    List<Event> getAllEventsForUser(Long userId, Integer from, Integer size);

    EventDtoFull addEventOnUser(Long userId, EventDto dto);

    Event getEventForUser(Long userId, Long eventId);

    Event updateEventOnUser(Long userId, Long eventId, EventDto dto);

    List<Request> getRequestsForUserOnEvent(Long userId, Long eventId);

    Request updateRequestOnUser(Long userId, Long eventId, RequestDto dto);

    List<Request> getRequestsForUser(Long userId);

    Request addRequestOnUser(Long userId, Long eventId);

    Request cancelRequestOnUser(Long userId, Long requestId);

    List<User> getUsersOnAdmin(Long[] users, Integer from, Integer size);

    User addUserOnAdmin(UserDto dto);

    void deleteUserOnAdmin(Long userID);

    Event updateEventOnAdmin(Long eventId, EventDtoState dto);

    List<EventDtoFull> getEventsForAdmin(Long[] users, String states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size);
}
