package major.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import major.events.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class EventDto {
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    @Size(min = 3, max = 120)
    private String title;
}
