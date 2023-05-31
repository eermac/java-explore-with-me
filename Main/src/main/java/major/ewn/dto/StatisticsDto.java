package major.ewn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StatisticsDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;

    public StatisticsDto() {

    }
}