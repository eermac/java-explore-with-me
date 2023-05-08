package statistics.service;

import statistics.dto.ResponseDto;
import statistics.dto.StatisticsDto;
import statistics.model.Statistics;

import java.util.Set;

public interface StatisticsService {
    Statistics add(StatisticsDto statistics);

    Set<ResponseDto> getStats(String start, String end, String[] uris, String unique);
}
