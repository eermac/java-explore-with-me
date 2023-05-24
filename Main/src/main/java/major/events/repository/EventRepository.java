package major.events.repository;

import major.events.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("Select e from Event e where e.initiator.id = ?1")
    Page<Event> getEvents(Long userId, Pageable page);

    @Query("Select e from Event e where e.initiator.id = ?1 AND e.id = ?2")
    Event getEvent(Long userId, Long eventId);

    @Query("Select e from Event e where e.id in (?1)")
    List<Event> getEventsById(Long[] eventsId);
}
