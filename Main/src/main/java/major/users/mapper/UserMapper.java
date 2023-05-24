package major.users.mapper;

import major.users.dto.UserDto;
import major.users.model.User;

public class UserMapper {
    public static User map(UserDto dto) {
        return new User(null, dto.getName(), dto.getEmail());
    }
}
