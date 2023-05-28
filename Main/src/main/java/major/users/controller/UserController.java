package major.users.controller;

import major.events.dto.EventDto;
import major.events.dto.EventDtoFull;
import major.events.dto.EventDtoState;
import major.events.dto.RequestsStatus;
import major.events.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import major.requests.dto.RequestDto;
import major.users.dto.UserDto;
import major.users.model.User;
import major.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    @GetMapping("/users/{userId}/events")
    public List<Event> getAllEventsForUser(@PathVariable Long userId,
                                           @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получаем все события текущего пользователя");
        return service.getAllEventsForUser(userId, from, size);
    }

    @PostMapping("/users/{userId}/events")
    public ResponseEntity<EventDtoFull> addEventOnUser(@PathVariable Long userId, @Valid @RequestBody EventDto dto) {
        log.info("Добавление нового события от текущего пользователя");
        return new ResponseEntity<>(service.addEventOnUser(userId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public Event getEventForUser(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получаем полную информацию по событию текущего пользователя");
        return service.getEventForUser(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventDtoFull updateEventOnUser(@PathVariable Long userId,
                                @PathVariable Long eventId,
                                @Valid @RequestBody EventDtoState dto) {
        log.info("Обновление события от текущего пользователя");
        return service.updateEventOnUser(userId, eventId, dto);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<RequestDto> getRequestsForUserOnEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Получаем запросы на событие текущего пользователя");
        return service.getRequestsForUserOnEvent(userId, eventId);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public List<RequestDto> updateRequestsForUserOnEvent(@PathVariable Long userId, @PathVariable Long eventId, @RequestBody RequestsStatus ids) {
        log.info("Обновляем запросы на событие текущего пользователя");
        return service.updateRequestsForUserOnEvent(userId, eventId, ids);
    }

    @GetMapping("/users/{userId}/requests")
    public List<RequestDto> getRequestsForUser(@PathVariable Long userId) {
        log.info("Получаем запросы текущего пользователя");
        return service.getRequestsForUser(userId);
    }

    @PostMapping("/users/{userId}/requests")
    public ResponseEntity<RequestDto> addRequestOnUser(@PathVariable Long userId, @RequestParam(name = "eventId") Long eventId) {
        log.info("Добавление нового запроса от текущего пользователя");
        return new ResponseEntity<>(service.addRequestOnUser(userId, eventId), HttpStatus.CREATED);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequestOnUser(@PathVariable Long userId,
                                       @PathVariable Long requestId) {
        log.info("Изменение статуса запроса от текущего пользователя");
        return service.cancelRequestOnUser(userId, requestId);
    }

    @GetMapping("/admin/users")
    public List<User> getUsersOnAdmin(@RequestParam(name = "ids", defaultValue = "") Long[] ids,
                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получаем пользователей");
        return service.getUsersOnAdmin(ids, from, size);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<User> addUserOnAdmin(@Valid @RequestBody UserDto dto) {
        log.info("Добавление нового пользователя");
        return new ResponseEntity<>(service.addUserOnAdmin(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<?> deleteUserOnAdmin(@PathVariable Long userId) {
        log.info("Удаление пользователя");
        service.deleteUserOnAdmin(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventDtoFull updateEventOnAdmin(@PathVariable Long eventId, @Valid @RequestBody EventDtoState dto) {
        log.info("Редактирование данных события");
        return service.updateEventOnAdmin(eventId, dto);
    }

    @GetMapping("/admin/events")
    public List<EventDtoFull> getEventsForAdmin(@RequestParam(name = "users", defaultValue = "0") Long[] users,
                                                      @RequestParam(name = "states", defaultValue = "0") String states,
                                                      @RequestParam(name = "categories", defaultValue = "0") Long[] categories,
                                                      @RequestParam(name = "rangeStart", defaultValue = "1970-01-01 00:00:00") String rangeStart,
                                                      @RequestParam(name = "rangeEnd", defaultValue = "1970-02-01 00:00:00") String rangeEnd,
                                                      @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Поиск событий");
        return service.getEventsForAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }
}