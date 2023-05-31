package major.requests.repository;

import major.requests.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("Select r from Request r where r.requester.id = ?1")
    List<Request> getRequestsForUser(Long requesterId);

    @Query("Select r from Request r where r.event.id = ?1")
    List<Request> getRequestOnEventForUser(Long eventId);

    @Query("Select r from Request r where r.id in (?1)")
    List<Request> getRequests(Long[] requests);

    @Query("Select r from Request r where r.requester.id = ?1 AND r.event.id = ?2")
    Request getRequestsForUser(Long userId, Long eventId);
}
