package major.ewn.controller;

import major.categories.model.Categories;
import major.categories.service.CategoriesService;
import major.compilations.model.Compilations;
import major.compilations.service.CompilationsService;
import major.events.dto.EventDtoFull;
import major.events.service.EventsService;
import major.ewn.StatsClient;
import major.ewn.dto.StatisticsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EwnController {
    private final StatsClient statsClient;
    private final EventsService eventsService;
    private final CompilationsService compilationsService;
    private final CategoriesService categoriesService;

    @GetMapping("/events")
    public List<EventDtoFull> getEvents(@RequestParam(name = "text", required = false) String text,
                                 @RequestParam(name = "categories", required = false) List<Long> categories,
                                 @RequestParam(name = "paid", required = false) String paid,
                                 @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                 @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                 @RequestParam(name = "onlyAvailable", defaultValue = "false") String onlyAvailable,
                                 @RequestParam(name = "sort", defaultValue = "EVENT_DATE") String sort,
                                 @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                 @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                 HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StatisticsDto statisticsDto = new StatisticsDto("ewn-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(formatter));

        statsClient.saveStats(statisticsDto);

        return eventsService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{id}")
    public EventDtoFull getEventsId(@PathVariable Long id, HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StatisticsDto statisticsDto = new StatisticsDto("ewn-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(formatter));

        statsClient.saveStats(statisticsDto);

        return eventsService.getEvent(id);
    }

    @GetMapping("/compilations")
    public List<Compilations> getAll(@RequestParam(name = "pinned", defaultValue = "false") String pinned,
                                     @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                     @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получаем все подборки");
        return compilationsService.getAll(Boolean.parseBoolean(pinned), from, size);
    }

    @GetMapping("/compilations/{compId}")
    public Compilations getCompilation(@PathVariable Long compId) {
        log.info("Получение подборки");
        return compilationsService.get(compId);
    }

    @GetMapping("/categories")
    public List<Categories> getAll(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                   @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получаем все категории");
        return categoriesService.getAll(from, size);
    }

    @GetMapping("/categories/{catId}")
    public Categories getCategories(@PathVariable Long catId) {
        log.info("Получаем категорию");
        return categoriesService.get(catId);
    }
}
