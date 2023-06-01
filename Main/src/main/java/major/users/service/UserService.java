package major.users.service;

import major.comments.dto.CommentDto;
import major.comments.model.Comment;
import major.events.dto.EventDto;
import major.events.dto.EventDtoFull;
import major.events.dto.EventDtoState;
import major.events.dto.RequestsStatus;
import major.events.model.Event;
import major.requests.dto.RequestDto;
import major.requests.dto.RequestsDto;
import major.users.dto.UserDto;
import major.users.model.User;

import java.util.List;

public interface UserService {
    List<Event> getAllEventsForUser(Long userId, Integer from, Integer size);

    EventDtoFull addEventOnUser(Long userId, EventDto dto);

    Event getEventForUser(Long userId, Long eventId);

    EventDtoFull updateEventOnUser(Long userId, Long eventId, EventDtoState dto);

    List<RequestDto> getRequestsForUserOnEvent(Long userId, Long eventId);

    RequestsDto updateRequestsForUserOnEvent(Long userId, Long eventId, RequestsStatus ids);

    List<RequestDto> getRequestsForUser(Long userId);

    RequestDto addRequestOnUser(Long userId, Long eventId);

    RequestDto cancelRequestOnUser(Long userId, Long requestId);

    List<User> getUsersOnAdmin(Long[] users, Integer from, Integer size);

    User addUserOnAdmin(UserDto dto);

    void deleteUserOnAdmin(Long userID);

    EventDtoFull updateEventOnAdmin(Long eventId, EventDtoState dto);

    List<EventDtoFull> getEventsForAdmin(Long[] users, String states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size);

    Comment addComment(Long userId, Long eventId, CommentDto dto);

    List<Comment> getCommentsForUser(Long userId, Long eventId, Integer from, Integer size);

    Comment getCommentForUser(Long userId, Long commentId);

    Comment updateComment(Long userId, CommentDto dto, Long commentId);

    void deleteComment(Long userId, Long commentId);

    void deleteCommentOnAdmin(Long commentId);

    void deleteAllCommentOnAdmin(Long userId);
}
