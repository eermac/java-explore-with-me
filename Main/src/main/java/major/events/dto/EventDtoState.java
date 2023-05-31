package major.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import major.events.model.Location;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class EventDtoState {
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    @Size(min = 3, max = 120)
    private String title;
    private String stateAction;

    public EventDtoState() {

    }
}
