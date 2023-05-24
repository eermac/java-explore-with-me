package major.compilations.repository;

import major.compilations.model.Compilations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompilationsRepository extends JpaRepository<Compilations, Long> {
    @Query("Select c from Compilations c where c.pinned = ?1")
    Page<Compilations> getCompilations(Boolean pinned, Pageable page);
}
