package major.requests.mapper;

import major.events.model.Event;
import major.requests.model.Request;
import major.users.model.User;

import java.time.LocalDateTime;

public class RequestMapper {
    public static Request map(User requester, Event event) {
        return new Request(null,
                LocalDateTime.now(),
                event,
                requester,
                "PENDING");
    }
}
