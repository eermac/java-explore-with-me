package major.requests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RequestsDto {
    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;
}
