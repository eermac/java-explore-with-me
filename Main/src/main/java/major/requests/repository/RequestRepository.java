package major.requests.repository;

import major.requests.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("Select r from Request r where r.requester.id = ?1")
    List<Request> getRequestsForUser(Long requesterId);
}
