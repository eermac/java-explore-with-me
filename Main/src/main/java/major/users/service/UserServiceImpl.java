package major.users.service;

import major.categories.model.Categories;
import major.categories.repository.CategoriesRepository;
import major.events.dto.EventDto;
import major.events.dto.EventDtoFull;
import major.events.dto.EventDtoState;
import major.events.dto.RequestsStatus;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (dto.getEventDate() != null
                && LocalDateTime.parse(dto.getEventDate(), formatter).isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (dto.getPaid() == null) dto.setPaid(false);
        if (dto.getParticipantLimit() == null) dto.setParticipantLimit(0L);
        if (dto.getRequestModeration() == null) dto.setRequestModeration(true);

        Categories categories = categoriesRepository.findById(dto.getCategory()).get();
        locationRepository.save(dto.getLocation());
        Event event = eventRepository.save(EventMapper.map(dto, repository.findById(userId).get(), categories));
        EventDtoFull eventDtoFull = EventMapper.map(event);
        eventDtoFull.setEventDate(dto.getEventDate());
        return eventDtoFull;
    }

    @Override
    public Event getEventForUser(Long userId, Long eventId) {
        return eventRepository.getEvent(userId, eventId);
    }

    @Override
    public EventDtoFull updateEventOnUser(Long userId, Long eventId, EventDtoState dto) {
        Event event = eventRepository.findById(eventId).get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Categories categories = new Categories();

        if (dto.getEventDate() != null
                && LocalDateTime.parse(dto.getEventDate(), formatter).isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (event.getState().equals(EventState.PUBLISHED)) throw new ResponseStatusException(HttpStatus.CONFLICT);

        if (event.getState().equals(EventState.PENDING)) {
            event.setState(EventState.CANCELED);
        } else if (dto.getStateAction() != null && dto.getStateAction().equals("SEND_TO_REVIEW")) {
            event.setState(EventState.PENDING);
        }

        if (dto.getCategory() != null) {
            categories = categoriesRepository.findById(dto.getCategory()).get();
        } else categories = categoriesRepository.findById(event.getCategory().getId()).get();
        EventDtoFull updateEvent = EventMapper.map(event);
        eventRepository.save(EventMapper.map(updateEvent, event, categories));



        return updateEvent;
    }

    @Override
    public List<RequestDto> getRequestsForUserOnEvent(Long userId, Long eventId) {
        List<Request> request = requestRepository.getRequestOnEventForUser(eventId);
        List<RequestDto> requestDtoList = new ArrayList<>();

        for (Request next: request) {
            requestDtoList.add(RequestMapper.map(next));
        }

        return requestDtoList;
    }

    @Override
    public List<RequestDto> updateRequestsForUserOnEvent(Long userId, Long eventId, RequestsStatus ids) {
        List<Request> requestList = requestRepository.getRequests(ids.getRequestIds());
        Event event = eventRepository.findById(eventId).get();
        List<RequestDto> requestDtoList = new ArrayList<>();

        for (Request next: requestList) {
            next.setStatus(ids.getStatus());
            if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            } else {
                requestRepository.save(next);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                eventRepository.save(event);
                requestDtoList.add(RequestMapper.map(next));
            }
        }

        return requestDtoList;
    }

    @Override
    public List<RequestDto> getRequestsForUser(Long userId) {
        List<Request> requestList = requestRepository.getRequestsForUser(userId);
        List<RequestDto> requestDtoList = new ArrayList<>();

        for (Request next: requestList) {
            requestDtoList.add(RequestMapper.map(next));
        }

        return requestDtoList;
    }

    @Override
    public RequestDto addRequestOnUser(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User requester = repository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (requestRepository.getRequestsForUser(userId, eventId) != null
                || event.getInitiator().getId().equals(userId)
                || !event.getState().equals(EventState.PUBLISHED)
            //    || requestRepository.getRequestOnEventForUser(eventId).size() >= event.getParticipantLimit()
                ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Request request = RequestMapper.map(requester, event);
        request.setStatus("PENDING");
        requestRepository.save(request);

        return RequestMapper.map(request);
    }

    @Override
    public RequestDto cancelRequestOnUser(Long userId, Long requestId) {
        Request request = requestRepository.findById(requestId).get();
        request.setStatus("CANCELED");
        requestRepository.save(request);
        return RequestMapper.map(request);
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
    public EventDtoFull updateEventOnAdmin(Long eventId, EventDtoState dto) {
        Event event = eventRepository.findById(eventId).get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Categories categories = new Categories();

        if (dto.getEventDate() != null
                && LocalDateTime.parse(dto.getEventDate(), formatter).isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (dto.getCategory() != null) {
             categories = categoriesRepository.findById(dto.getCategory()).get();
        }

        Event newEvent = EventMapper.map(event, dto, categories);

        if (newEvent.getState().equals(EventState.PUBLISHED) || newEvent.getState().equals(EventState.CANCELED)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (dto.getStateAction() != null && dto.getStateAction().equals("PUBLISH_EVENT")) {
            newEvent.setState(EventState.PUBLISHED);
            newEvent.setPublishedOn(LocalDateTime.now());
        } else newEvent.setState(EventState.CANCELED);

        if (dto.getLocation() != null) locationRepository.save(dto.getLocation());
        eventRepository.save(newEvent);
        EventDtoFull eventDto = EventMapper.map(event);
        String newDate = event.getEventDate().format(formatter);
        eventDto.setEventDate(newDate);
        return eventDto;
    }

    @Override
    public List<EventDtoFull> getEventsForAdmin(Long[] users, String states, Long[] categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime rangeStartFormat = LocalDateTime.parse(rangeStart, formatter);
        LocalDateTime rangeEndFormat = LocalDateTime.parse(rangeEnd, formatter);
        List<Event> eventList = eventRepository.getEventsWithFilter(users, states, categories, rangeStartFormat, rangeEndFormat, page).getContent();
        List<EventDtoFull> eventDtoFulls = new ArrayList<>();

        for (Event next: eventList) {
            eventDtoFulls.add(EventMapper.map(next));
        }

        return eventDtoFulls;
    }
}