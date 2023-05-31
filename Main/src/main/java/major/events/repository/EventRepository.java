package major.events.repository;

import major.events.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("Select e from Event e where e.initiator.id = ?1")
    Page<Event> getEvents(Long userId, Pageable page);

    @Query("Select e from Event e where e.initiator.id = ?1 AND e.id = ?2")
    Event getEvent(Long userId, Long eventId);

    @Query("Select e from Event e where e.id in (?1)")
    Set<Event> getEventsById(Long[] eventsId);

    @Query("Select e from Event e where e.initiator.id in (?1) AND " +
            "((?2 = 'PUBLISHED' AND e.state = 'PUBLISHED') OR (?2 = 'PENDING' AND e.state = 'PENDING')) " +
            "AND e.category.id in (?3) AND (e.eventDate BETWEEN ?4 AND ?5) ")
    Page<Event> getEventsWithFilter(Long[] users, String state, Long[] category, LocalDateTime startDate, LocalDateTime endDate, Pageable page);

    @Query("Select e from Event e where ((?1 is null) or upper(e.description) like upper(concat('%', ?1, '%')) or upper(e.annotation) like upper(concat('%', ?1, '%'))) " +
            "and (?2 is null or e.category.id in (?2)) and (?3 is null or e.paid = ?3) and ((cast(?4 as timestamp) is null and cast(?5 as timestamp) is null) or (e.eventDate BETWEEN cast(?4 as timestamp) AND cast(?5 as timestamp))) " +
            "and (?6 is null or ?6 = false or (e.confirmedRequests < e.participantLimit)) " +
            "and e.state = 'PUBLISHED'")
    Page<Event> getEventsPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStartFormat, LocalDateTime rangeEndFormat, Boolean onlyAvailable, String sort, Pageable pageable);
}
