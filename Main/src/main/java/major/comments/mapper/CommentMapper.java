package major.comments.mapper;

import major.comments.dto.CommentDto;
import major.comments.model.Comment;
import major.events.model.Event;
import major.users.model.User;

public class CommentMapper {
    public static Comment map(User user, Event event, CommentDto dto) {
        return new Comment(null,
                dto.getMessage(),
                user,
                event);
    }
}
