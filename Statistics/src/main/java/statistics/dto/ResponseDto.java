package statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDto {
    private String app;
    private String uri;
    private Long hits;
}
