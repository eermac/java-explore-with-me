package major.comments.repository;

import major.comments.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("Select c from Comment c where c.user.id = ?1 and c.event.id = ?2")
    Page<Comment> getAllCommentsForUser(Long userId, Long eventId, Pageable page);

    @Query("Select c.id from Comment c where c.user.id = ?1")
    List<Long> getIdCommentsForUser(Long userId);
}
