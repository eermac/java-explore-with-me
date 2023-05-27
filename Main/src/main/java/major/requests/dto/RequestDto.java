package major.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import major.events.model.Event;
import major.users.model.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class RequestDto {
    private Long id;
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private String status;
}
