package ewn.controller;

import ewn.StatsClient;
import ewn.dto.StatisticsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EwnController {
    private final StatsClient statsClient;

    @GetMapping("/events")
    public ResponseEntity<Object> getEvents(HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StatisticsDto statisticsDto = new StatisticsDto("ewn-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(formatter));

        log.info("\n!!!!all\n" + statisticsDto + "\n!!!\n");

        //statsClient.saveStats(statisticsDto);

        return statsClient.saveStats(statisticsDto);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Object> getEventsId(@PathVariable Long id, HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StatisticsDto statisticsDto = new StatisticsDto("ewn-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(formatter));

        log.info("\n!!!!id\n" + statisticsDto + "\n!!!\n");

        statsClient.saveStats(statisticsDto);

        return null;
    }
}
