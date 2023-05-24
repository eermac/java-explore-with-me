package major.compilations.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import major.events.model.Event;

import javax.persistence.*;
import java.util.List;

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
    @Transient
    private List<Event> events; //many to many
    private Boolean pinned;
    private String title;

    public Compilations() {
        super();
    }
}
