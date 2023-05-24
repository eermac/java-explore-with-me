package major.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import major.events.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class EventDto {
    @NotEmpty
    @NotBlank
    @NotNull
    private String annotation;
    private Long category;
    @NotEmpty
    @NotBlank
    @NotNull
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private String title;
}
