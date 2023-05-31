package major.compilations.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import major.events.model.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "compilations", schema = "public")
@Getter
@AllArgsConstructor
@Setter
@ToString
public class Compilations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(name = "compilations_events",
            joinColumns = { @JoinColumn(name = "compilations_id")},
            inverseJoinColumns = { @JoinColumn(name = "event_id")})
    private Set<Event> events;
    private Boolean pinned;
    private String title;

    public Compilations() {
        super();
    }
}
