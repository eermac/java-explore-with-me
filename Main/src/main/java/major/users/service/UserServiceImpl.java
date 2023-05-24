package major.users.service;

import major.categories.model.Categories;
import major.categories.repository.CategoriesRepository;
import major.events.dto.EventDto;
import major.events.dto.EventDtoFull;
import major.events.dto.EventDtoState;
import major.events.mapper.EventMapper;
import major.events.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import major.events.model.EventState;
import major.events.repository.EventRepository;
import major.events.repository.LocationRepository;
import major.requests.dto.RequestDto;
import major.requests.mapper.RequestMapper;
import major.requests.model.Request;
import major.requests.repository.RequestRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import major.users.dto.UserDto;
import major.users.mapper.UserMapper;
import major.users.model.User;
import major.users.repository.UserRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final CategoriesRepository categoriesRepository;

    @Override
    public List<Event> getAllEventsForUser(Long userId, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return eventRepository.getEvents(userId, page).getContent();
    }

    @Override
    public EventDtoFull addEventOnUser(Long userId, EventDto dto) {
            Categories categories= categoriesRepository.findById(dto.getCategory()).get();
            locationRepository.save(dto.getLocation());
            Event event = eventRepository.save(EventMapper.map(dto, repository.findById(userId).get(), categories));
            return EventMapper.map(event);
    }

    @Override
    public Event getEventForUser(Long userId, Long eventId) {
        return eventRepository.getEvent(userId, eventId);
    }

    @Override
    public Event updateEventOnUser(Long userId, Long eventId, EventDto dto) {
        return null;
    }

    @Override
    public List<Request> getRequestsForUserOnEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public Request updateRequestOnUser(Long userId, Long eventId, RequestDto dto) {
        return null;
    }

    @Override
    public List<Request> getRequestsForUser(Long userId) {
        return requestRepository.getRequestsForUser(userId);
    }

    @Override
    public Request addRequestOnUser(Long userId, Long eventId) {
        User requester = repository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.info("\n!!!!\n" + requester + "\n!!!\n");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.info("\n!!!!\n" + event + "\n!!!\n");
        return requestRepository.save(RequestMapper.map(requester, event));
    }

    @Override
    public Request cancelRequestOnUser(Long userId, Long requestId) {
        Request request = requestRepository.findById(requestId).get();
        request.setStatus("CANCEL");
        return requestRepository.save(request);
    }

    @Override
    public List<User> getUsersOnAdmin(Long[] users, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);

        if (users.length > 0) return repository.getUsers(users, page).getContent();
        else return repository.findAll(page).getContent();
    }

    @Override
    public User addUserOnAdmin(UserDto dto) {
        try {
            return repository.save(UserMapper.map(dto));
        } catch (DataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Override
    public void deleteUserOnAdmin(Long userID) {
        repository.deleteById(userID);
    }

    @Override
    public Event updateEventOnAdmin(Long eventId, EventDtoState dto) {
        Event event = eventRepository.findById(eventId).get();
        if (dto.getStateAction().equals("PUBLISH_EVENT")) event.setState(EventState.PUBLISHED);
        else event.setState(EventState.CANCELED);
        return eventRepository.save(event);
    }
}
