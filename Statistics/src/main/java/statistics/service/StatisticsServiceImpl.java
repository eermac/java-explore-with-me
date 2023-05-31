package statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import statistics.dto.ResponseDto;
import statistics.dto.StatisticsDto;
import statistics.mapper.StatisticsMapper;
import statistics.model.Statistics;
import statistics.repository.StatisticsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository repository;

    Comparator<ResponseDto> userComparator = new Comparator<>() {
        @Override
        public int compare(ResponseDto stat1, ResponseDto stat2) {
            if (stat1.getHits() < (stat2.getHits())) return 1;
            else return -1;
        }
    };

    @Override
    public Statistics add(StatisticsDto statistics) {
        return repository.save(StatisticsMapper.map(statistics));
    }

    @Override
    public Set<ResponseDto> getStats(String start, String end, String[] uris, String unique) {
        List<Statistics> statisticsList = new ArrayList<>();
        Set<ResponseDto> responseDtoList = new TreeSet<>(userComparator);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (LocalDateTime.parse(start, formatter).isAfter(LocalDateTime.parse(end, formatter))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (uris.length > 0) {
            for (String next: uris) {
                statisticsList.add(repository.findTop1ByUri(next));
            }
        } else statisticsList = repository.findAll();

        if (Boolean.parseBoolean(unique)) {
            for (Statistics next: statisticsList) {
                responseDtoList.add(new ResponseDto(next.getApp(), next.getUri(), repository.findDistinct(next.getUri())));
            }
        } else {
            for (Statistics next: statisticsList) {
                responseDtoList.add(new ResponseDto(next.getApp(), next.getUri(), repository.find(next.getUri())));
            }
        }

        return responseDtoList;
    }
}
