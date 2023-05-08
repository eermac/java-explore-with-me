package statistics.mapper;

import statistics.dto.StatisticsDto;
import statistics.model.Statistics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatisticsMapper {
    public static Statistics map(StatisticsDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return new Statistics(null,
                dto.getApp(),
                dto.getUri(),
                dto.getIp(),
                LocalDateTime.parse(dto.getTimestamp(), formatter));
    }
}
