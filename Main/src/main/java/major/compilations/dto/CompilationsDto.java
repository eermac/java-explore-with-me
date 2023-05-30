package major.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class CompilationsDto {
    private Long[] events;
    private Boolean pinned;
    @Size(max = 50)
    private String title;
}
