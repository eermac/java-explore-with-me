package major.comments.model;

import lombok.*;
import major.events.model.Event;
import major.users.model.User;

import javax.persistence.*;

@Entity
@Table(name = "comments", schema = "public")
@Getter
@AllArgsConstructor
@Setter
@ToString
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}