package major.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestsStatus {
    private Long[] requestIds;
    private String status;
}
