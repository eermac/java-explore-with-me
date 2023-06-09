package major.compilations.controller;

import major.compilations.dto.CompilationsDto;
import major.compilations.model.Compilations;
import major.compilations.service.CompilationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Slf4j
public class CompilationsController {
    private final CompilationsService service;

    @PostMapping
    public ResponseEntity<Compilations> add(@Valid @RequestBody CompilationsDto dto) {
        log.info("Добавление новой подборки");
        return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<?> delete(@PathVariable Long compId) {
        log.info("Удаление подборки");
        service.delete(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{compId}")
    public Compilations update(@RequestBody CompilationsDto dto, @PathVariable Long compId) {
        if (dto.getTitle() == null) dto.setTitle("default");
        else if (dto.getTitle().length() > 50) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        log.info("Обновление подборки");
        return service.update(compId, dto);
    }
}