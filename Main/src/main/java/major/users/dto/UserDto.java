package major.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor
@Data
public class UserDto {
    @NotEmpty
    @NotNull
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
    @Email
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 254)
    private String email;
}
