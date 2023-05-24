package major.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class CategoriesDto {
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String name;

    public CategoriesDto() {}
}
