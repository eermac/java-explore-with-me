package major.users.repository;

import major.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("Select u from User u where u.id in (?1)")
    Page<User> getUsers(Long[] users, Pageable page);
}
