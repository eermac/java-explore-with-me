package statistics.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import statistics.dto.ResponseDto;
import statistics.dto.StatisticsDto;
import statistics.model.Statistics;
import statistics.service.StatisticsService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {
    private final StatisticsService statisticsService;

    @PostMapping("/hit")
    public ResponseEntity<Statistics> add(@Valid @RequestBody StatisticsDto statistics) {
        log.info("Записываем запрос");
        return new ResponseEntity<Statistics>(statisticsService.add(statistics), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public Set<ResponseDto> getAll(@RequestParam(name = "start") String start,
                                   @RequestParam(name = "end") String end,
                                   @RequestParam(name = "uris", defaultValue = "") String[] uris,
                                   @RequestParam(name = "unique", defaultValue = "false") String unique) {
        log.info("Получаем статистику");
        return statisticsService.getStats(start, end, uris, unique);
    }
}
