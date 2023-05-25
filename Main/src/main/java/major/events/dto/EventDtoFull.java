package major.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import major.categories.model.Categories;
import major.events.model.EventState;
import major.events.model.Location;
import major.users.model.User;

@AllArgsConstructor
@Data
public class EventDtoFull {
    private Long id;
    private String title;
    private String annotation;
    private Categories category;
    private Boolean paid;
    private String eventDate;
    private User initiator;
    private String description;
    private Long participantLimit;
    private EventState state;
    private String createdOn;
    private Location location;
    private Boolean requestModeration;
    private String publishedOn;
    private Long confirmedRequests;
    private Long views;
}
