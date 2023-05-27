package major.events.repository;

import major.events.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
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

    @Query("Select e from Event e where " +
            "(upper(e.description) like upper(concat('%', ?1, '%')) or upper(e.annotation) like upper(concat('%', ?1, '%'))) " +
            "AND e.category.id in (?2) AND e.paid = ?3 AND (e.eventDate BETWEEN ?4 AND ?5) " +
            "and ?6 = false")
    Page<Event> getEventsPublic(String text, Long[] category, Boolean paid, LocalDateTime startDate, LocalDateTime endDate, Boolean onlyAvailable, Pageable page);
}
