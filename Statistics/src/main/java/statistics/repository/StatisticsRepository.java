package statistics.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import statistics.model.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    @Query("Select count(distinct s.ip) from Statistics s group by s.uri having s.uri = (?1)")
    Long findDistinct(String uri);

    @Query("Select count(s.ip) from Statistics s group by s.uri having s.uri = (?1)")
    Long find(String uri);

    Statistics findTop1ByUri(String uri);
}
