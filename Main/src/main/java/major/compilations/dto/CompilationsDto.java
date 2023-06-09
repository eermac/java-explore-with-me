package major.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class CompilationsDto {
    private Long[] events;
    private Boolean pinned;
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 50)
    private String title;
}
