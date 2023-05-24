package major.compilations.controller;

import major.compilations.dto.CompilationsDto;
import major.compilations.model.Compilations;
import major.compilations.service.CompilationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompilationsController {
    private final CompilationsService service;

    @PostMapping("/admin/compilations")
    public ResponseEntity<Compilations> add(@Valid @RequestBody CompilationsDto dto) {
        log.info("Добавление новой подборки");
        return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public ResponseEntity<?> delete(@PathVariable Long compId) {
        log.info("Удаление подборки");
        service.delete(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public Compilations update(@Valid @RequestBody CompilationsDto dto, @PathVariable Long compId) {
        log.info("Обновление подборки");
        return service.update(compId, dto);
    }

    @GetMapping("/compilations")
    public List<Compilations> getAll(@RequestParam(name = "pinned", defaultValue = "false") String pinned,
                                     @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                     @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получаем все подборки");
        return service.getAll(Boolean.parseBoolean(pinned), from, size);
    }

    @GetMapping("/compilations/{compId}")
    public Compilations get(@PathVariable Long compId) {
        log.info("Получение подборки");
        return service.get(compId);
    }
}
