package major.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import major.categories.model.Categories;
import major.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events", schema = "public")
@Getter
@AllArgsConstructor
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String annotation;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Categories category;
    private Boolean paid;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @OneToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private String description;
    @Column(name = "participant_limit")
    private Long participantLimit;
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Column(name = "created")
    private LocalDateTime createdOn;
    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "confirmed_requests")
    private Long confirmedRequests;
    private Long views;

    public Event() {
        super();
    }
}

