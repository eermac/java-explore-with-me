package major.categories.controller;

import major.categories.dto.CategoriesDto;
import major.categories.model.Categories;
import major.categories.service.CategoriesService;
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
public class CategoriesController {
    private final CategoriesService service;

    @PostMapping("/admin/categories")
    public ResponseEntity<Categories> add(@Valid @RequestBody CategoriesDto categories) {
        log.info("Добавляем новую категорию");
        return new ResponseEntity<>(service.add(categories), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{catId}")
    public ResponseEntity<?> delete(@PathVariable Long catId) {
        log.info("Удаляем категорию");
        service.delete(catId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/admin/categories/{catId}")
    public Categories update(@PathVariable Long catId, @Valid @RequestBody CategoriesDto categories) {
        log.info("Обновляем категорию");
        return service.update(catId, categories);
    }

    @GetMapping("/categories")
    public List<Categories> getAll(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Получаем все категории");
        return service.getAll(from, size);
    }

    @GetMapping("/categories/{catId}")
    public Categories get(@PathVariable Long catId) {
        log.info("Получаем категорию");
        return service.get(catId);
    }
}
